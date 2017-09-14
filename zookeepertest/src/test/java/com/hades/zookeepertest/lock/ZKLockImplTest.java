package com.hades.zookeepertest.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLongArray;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ZKLockImplTest {

    private final String testLockName = "testLockName";

    @Before
    public void before() {
        ZKLockImpl.initRoot();
    }

    @Test
    public void testLock() {
        System.out.println("ZKLockImplTest.testLock()");
        int num = 5;
        long millis = 200l;
        AtomicLongArray ala = new AtomicLongArray(num);
        List<Thread> list = new ArrayList<Thread>();
        for (int i = 0; i < num; i++) {
            ZKLock zkLock = new ZKLockImpl(testLockName);
            TestLockRunnable runnable = new TestLockRunnable(zkLock, millis, ala);
            list.add(new Thread(runnable));
        }
        long currentTimeMillis = System.currentTimeMillis();
        for (Thread thread : list) {
            thread.start();
        }
        while (true) {
            boolean flag = true;
            for (Thread thread : list) {
                if (thread.isAlive()) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                break;
            }
        }
        long temp = -1;
        for (int i = 0, len = ala.length(); i < len; i++) {
            if (temp != -1) {
                long a = ala.get(i) - temp;
                System.out.println((ala.get(i) - currentTimeMillis) + "\t\t+" + a);
                Assert.assertTrue(a >= millis);
            } else {
                System.out.println(ala.get(i) - currentTimeMillis);
            }
            temp = ala.get(i);
        }
    }

    @Test
    public void testTryLock0() {
        System.out.println("ZKLockImplTest.testTryLock0()");
        int num = 5;
        long millis = 200l;
        AtomicLongArray ala = new AtomicLongArray(num);
        List<Thread> list = new ArrayList<Thread>();
        for (int i = 0; i < num; i++) {
            ZKLock zkLock = new ZKLockImpl(testLockName);
            TestTryLock0Runnable runnable = new TestTryLock0Runnable(zkLock, millis, ala);
            list.add(new Thread(runnable));
        }
        long currentTimeMillis = System.currentTimeMillis();
        for (Thread thread : list) {
            thread.start();
        }
        while (true) {
            boolean flag = true;
            for (Thread thread : list) {
                if (thread.isAlive()) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                break;
            }
        }
        for (int i = 0, len = ala.length(); i < len; i++) {
            if (i == 0) {
                System.out.println(ala.get(i) - currentTimeMillis);
                Assert.assertTrue(ala.get(i) > currentTimeMillis);
            } else {
                System.out.println(ala.get(i));
                Assert.assertTrue(ala.get(i) == 0l);
            }
        }
    }

    @Test
    public void testTryLock1() {
        System.out.println("ZKLockImplTest.testTryLock1()");
        int num = 10;
        long millis = 200l;
        AtomicLongArray ala = new AtomicLongArray(num);
        List<Thread> list = new ArrayList<Thread>();
        for (int i = 0; i < num; i++) {
            ZKLock zkLock = new ZKLockImpl(testLockName);
            TestTryLock1Runnable runnable = new TestTryLock1Runnable(zkLock, millis, ala, (millis * num / 2));
            list.add(new Thread(runnable));
        }
        long currentTimeMillis = System.currentTimeMillis();
        for (Thread thread : list) {
            thread.start();
        }
        while (true) {
            boolean flag = true;
            for (Thread thread : list) {
                if (thread.isAlive()) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                break;
            }
        }
        for (int i = 0, len = ala.length(); i < len; i++) {
            if (i < 5) {
                System.out.println(ala.get(i) - currentTimeMillis);
                Assert.assertTrue(ala.get(i) > currentTimeMillis);
            } else {
                System.out.println(ala.get(i));
                Assert.assertTrue(ala.get(i) == 0l);
            }
        }
    }

    class TestLockRunnable implements Runnable {

        ZKLock zkLock;
        long millis;
        AtomicLongArray ala;

        public TestLockRunnable(ZKLock zkLock, long millis, AtomicLongArray ala) {
            this.zkLock = zkLock;
            this.millis = millis;
            this.ala = ala;
        }

        @Override
        public void run() {
            zkLock.lock();
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            zkLock.unlock();
            afterAwait();
        }

        protected void afterAwait() {
            long currentTimeMillis = System.currentTimeMillis();
            for (int i = 0, len = ala.length(); i < len; i++) {
                if (ala.compareAndSet(i, 0l, currentTimeMillis)) {
                    break;
                }
            }
        }
    }

    class TestTryLock0Runnable implements Runnable {

        ZKLock zkLock;
        long millis;
        AtomicLongArray ala;

        public TestTryLock0Runnable(ZKLock zkLock, long millis, AtomicLongArray ala) {
            this.zkLock = zkLock;
            this.millis = millis;
            this.ala = ala;
        }

        @Override
        public void run() {
            boolean tryLock = zkLock.tryLock();
            if (tryLock) {
                try {
                    Thread.sleep(millis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                zkLock.unlock();
                afterAwait();
            }
        }

        protected void afterAwait() {
            long currentTimeMillis = System.currentTimeMillis();
            for (int i = 0, len = ala.length(); i < len; i++) {
                if (ala.compareAndSet(i, 0l, currentTimeMillis)) {
                    break;
                }
            }
        }
    }

    class TestTryLock1Runnable implements Runnable {

        ZKLock zkLock;
        long millis;
        AtomicLongArray ala;
        long time;

        public TestTryLock1Runnable(ZKLock zkLock, long millis, AtomicLongArray ala, long time) {
            this.zkLock = zkLock;
            this.millis = millis;
            this.ala = ala;
            this.time = time;
        }

        @Override
        public void run() {
            boolean tryLock = zkLock.tryLock(time, TimeUnit.MILLISECONDS);
            if (tryLock) {
                try {
                    Thread.sleep(millis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                zkLock.unlock();
                afterAwait();
            }
        }

        protected void afterAwait() {
            long currentTimeMillis = System.currentTimeMillis();
            for (int i = 0, len = ala.length(); i < len; i++) {
                if (ala.compareAndSet(i, 0l, currentTimeMillis)) {
                    break;
                }
            }
        }
    }
}
