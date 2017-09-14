package com.hades.zookeepertest.id;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IdBuilder {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ZkClient client;
    private final String server = "10.18.224.106:2181,10.18.224.106:2182,10.18.224.106:2183";
    private final String root = "/root/idBuilder";
    private final String nodePath;
    private final int subIndex;
    private boolean running = false;

    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final WriteLock wLock = rwLock.writeLock();
    private final ReadLock rLock = rwLock.readLock();

    public IdBuilder(String idName) {
        nodePath = root + "/" + idName;
        subIndex = nodePath.length();
        client = new ZkClient(server, 5000, 5000, new BytesPushThroughSerializer());
    }

    public void start() {
        wLock.lock();
        try {
            try {
                client.createPersistent(root, true);
            } catch (ZkNodeExistsException e) {
                logger.info("already exist : " + root);
            }
            running = true;
        } finally {
            wLock.unlock();
        }
    }

    public void stop() {
        wLock.lock();
        try {
            if (client != null) {
                client.close();
            }
            running = false;
        } finally {
            wLock.unlock();
        }
    }

    public String generateId() {
        rLock.lock();
        try {
            if (running) {
                String path = client.createPersistentSequential(nodePath, null);
                client.delete(path);
                return path.substring(subIndex);
            } else {
                return null;
            }
        } finally {
            rLock.unlock();
        }
    }

    public boolean isRunning() {
        return running;
    }
}
