package com.hades.zookeepertest.lock;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 对集群中其他节点有效，不能将同一个锁对象用于单机的多个线程，不可重入
 * 
 * @author HaDeS
 *
 */
public class ZKLockImpl implements ZKLock {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZKLockImpl.class);
    private static final String SERVER = "10.18.224.106:2181,10.18.224.106:2182,10.18.224.106:2183";
    private static final String ROOT = "/root/lock";
    private static final Pattern SEQ_PATTERN = Pattern.compile("^\\d{10}$");

    private final ZkClient client;
    private final String nodePath;
    private final String lockName;
    private final int subIndex;

    private String seqNodePath = null;

    public static void initRoot() {
        ZkClient client = new ZkClient(SERVER, 5000, 5000, new BytesPushThroughSerializer());
        client.createPersistent(ROOT, true);
        client.close();
        LOGGER.debug("init root : " + ROOT);
    }

    public ZKLockImpl(String lockName) {
        this.lockName = lockName;
        nodePath = ROOT + "/" + lockName;
        subIndex = nodePath.length();
        client = new ZkClient(SERVER, 5000, 5000, new BytesPushThroughSerializer());
    }

    /**
     * 
     * @return lockName
     */
    public String getLockName() {
        return lockName;
    }

    private String createNode() {
        return client.createEphemeralSequential(nodePath, null);
    }

    private String getSeq(String seqPath) {
        return seqPath.substring(subIndex);
    }

    private String getChildrenSeq(String childrenPath) {
        String temp = childrenPath.replaceFirst(lockName, "");
        return SEQ_PATTERN.matcher(temp).matches() ? temp : null;
    }

    private void validateChildren(final String seq, final CountDownLatch countDownLatch) {
        List<String> childrenList = client.getChildren(ROOT);
        String existMaxSeq = null;
        String maxSeqPath = null;
        String temp;
        for (String str : childrenList) {
            temp = getChildrenSeq(str);
            if (temp != null && seq.compareTo(temp) > 0 && (existMaxSeq == null || existMaxSeq.compareTo(temp) > 0)) {
                existMaxSeq = temp;
                maxSeqPath = str;
            }
        }
        if (existMaxSeq != null) {
            maxSeqPath = ROOT + "/" + maxSeqPath;
            IZkDataListener zkDataDeleteListener = new IZkDataListener() {

                @Override
                public void handleDataDeleted(String dataPath) throws Exception {
                    validateChildren(seq, countDownLatch);
                    client.unsubscribeDataChanges(dataPath, this);
                }

                @Override
                public void handleDataChange(String dataPath, Object data) throws Exception {
                }
            };
            // LOGGER.debug("seq=" + seq + ", maxSeqPath=" + maxSeqPath);
            client.subscribeDataChanges(maxSeqPath, zkDataDeleteListener);
        } else {
            countDownLatch.countDown();
        }
    }

    /**
     * 获取锁。 如果该锁没有被其他节点保持，则获取该锁并立即返回。 <br>
     * 
     * 如果该锁被另一个节点保持，禁用当前线程，并且在获得锁之前，该线程将一直处于休眠状态。
     */
    @Override
    public void lock() {
        seqNodePath = createNode();
        String seq = getSeq(seqNodePath);
        CountDownLatch countDownLatch = new CountDownLatch(1);

        List<String> childrenList = client.getChildren(ROOT);
        String existMaxSeq = null;
        String maxSeqPath = null;
        String temp;

        for (String str : childrenList) {
            temp = getChildrenSeq(str);
            if (temp != null && seq.compareTo(temp) > 0 && (existMaxSeq == null || existMaxSeq.compareTo(temp) > 0)) {
                existMaxSeq = temp;
                maxSeqPath = str;
            }
        }
        if (existMaxSeq != null) {
            maxSeqPath = ROOT + "/" + maxSeqPath;
            IZkDataListener zkDataDeleteListener = new IZkDataListener() {

                @Override
                public void handleDataDeleted(String dataPath) throws Exception {
                    validateChildren(seq, countDownLatch);
                    client.unsubscribeDataChanges(dataPath, this);
                }

                @Override
                public void handleDataChange(String dataPath, Object data) throws Exception {
                }
            };
            // LOGGER.debug("seq=" + seq + ", maxSeqPath=" + maxSeqPath);
            client.subscribeDataChanges(maxSeqPath, zkDataDeleteListener);

            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                unlock();
                throw new ZKLockException(e);
            }
        }
    }

    /**
     * 仅在调用时锁未被另一个节点保持的情况下，才获取该锁。
     * 
     * @return 如果锁是自由的并且被当前节点获取，则返回 true；否则返回 false
     */
    @Override
    public boolean tryLock() {
        boolean flag = false;

        seqNodePath = createNode();
        String seq = getSeq(seqNodePath);

        List<String> childrenList = client.getChildren(ROOT);
        String existMaxSeq = null;
        String temp;
        for (String str : childrenList) {
            temp = getChildrenSeq(str);
            if (temp != null && seq.compareTo(temp) > 0 && (existMaxSeq == null || existMaxSeq.compareTo(temp) > 0)) {
                existMaxSeq = temp;
            }
        }
        if (existMaxSeq != null) {
            unlock();
        } else {
            flag = true;
        }

        return flag;
    }

    /**
     * 如果锁在给定的等待时间内空闲，并且当前线程未被中断，则获取锁。<br>
     * 
     * 如果锁可用，则此方法将立即返回值 true。如果锁不可用，并且在发生以下三种情况之一前，该线程将一直处于休眠状态：<br>
     * 
     * 锁由当前线程获得；其他某个线程中断当前线程，并且支持对锁获取的中断；已超过指定的等待时间
     * 
     * @param time
     *            等待锁的最长时间
     * @param unit
     *            参数的时间单位
     * @return 如果获得了锁，则返回 true；如果在获取锁前超过了等待时间，则返回 false
     */
    @Override
    public boolean tryLock(long time, TimeUnit unit) {
        boolean flag = false;

        seqNodePath = createNode();
        String seq = getSeq(seqNodePath);
        CountDownLatch countDownLatch = new CountDownLatch(1);

        List<String> childrenList = client.getChildren(ROOT);
        String existMaxSeq = null;
        String maxSeqPath = null;
        String temp;
        for (String str : childrenList) {
            temp = getChildrenSeq(str);
            if (temp != null && seq.compareTo(temp) > 0 && (existMaxSeq == null || existMaxSeq.compareTo(temp) > 0)) {
                existMaxSeq = temp;
                maxSeqPath = str;
            }
        }
        if (existMaxSeq != null) {
            maxSeqPath = ROOT + "/" + maxSeqPath;
            IZkDataListener zkDataDeleteListener = new IZkDataListener() {

                @Override
                public void handleDataDeleted(String dataPath) throws Exception {
                    validateChildren(seq, countDownLatch);
                    client.unsubscribeDataChanges(dataPath, this);
                }

                @Override
                public void handleDataChange(String dataPath, Object data) throws Exception {
                }
            };
            // LOGGER.debug("seq=" + seq + ", maxSeqPath=" + maxSeqPath);
            client.subscribeDataChanges(maxSeqPath, zkDataDeleteListener);

            try {
                flag = countDownLatch.await(time, unit);
            } catch (InterruptedException e) {
                unlock();
                throw new ZKLockException(e);
            }
            if (!flag) {
                unlock();
            }
        } else {
            flag = true;
        }

        return flag;
    }

    /**
     * 释放此锁
     */
    @Override
    public void unlock() {
        if (seqNodePath != null) {
            client.delete(seqNodePath);
            seqNodePath = null;
        }
        client.close();
    }
}
