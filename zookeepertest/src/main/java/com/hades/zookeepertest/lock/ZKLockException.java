package com.hades.zookeepertest.lock;

@SuppressWarnings("serial")
public class ZKLockException extends RuntimeException {

    public ZKLockException() {
        super();
    }

    public ZKLockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ZKLockException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZKLockException(String message) {
        super(message);
    }

    public ZKLockException(Throwable cause) {
        super(cause);
    }
}
