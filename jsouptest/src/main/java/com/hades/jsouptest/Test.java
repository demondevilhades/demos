package com.hades.jsouptest;

import org.apache.log4j.Logger;

public class Test {

    private static final Logger LOGGER = Logger.getLogger(Test.class);
    
    public static void main(String[] args) throws Exception {
        LOGGER.debug("aaa", new RuntimeException("bbb"));
        Thread.sleep(100L);
        LOGGER.info("aaa", new RuntimeException("bbb"));
        Thread.sleep(100L);
        LOGGER.warn("aaa", new RuntimeException("bbb"));
        Thread.sleep(100L);
        LOGGER.error("aaa", new RuntimeException("bbb"));
        Thread.sleep(100L);
        LOGGER.fatal("aaa", new RuntimeException("bbb"));
    }
}
