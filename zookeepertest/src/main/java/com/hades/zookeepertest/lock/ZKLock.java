package com.hades.zookeepertest.lock;

import java.util.concurrent.TimeUnit;

public interface ZKLock {

    public void lock();

    public boolean tryLock();

    public boolean tryLock(long time, TimeUnit unit);

    public void unlock();
}
