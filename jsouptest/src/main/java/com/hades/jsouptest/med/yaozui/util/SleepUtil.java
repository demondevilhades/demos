package com.hades.jsouptest.med.yaozui.util;

import org.apache.commons.lang3.RandomUtils;

public class SleepUtil {

    private static final boolean sleepFlag = true;

    public static void sleep60() {
        if (sleepFlag) {
            try {
                Thread.sleep(RandomUtils.nextInt(30, 90) * 1000);
            } catch (InterruptedException e) {
            }
        }
    }

    public static void sleep20() {
        if (sleepFlag) {
            try {
                Thread.sleep(RandomUtils.nextInt(10, 30) * 1000);
            } catch (InterruptedException e) {
            }
        }
    }

    public static void sleep10() {
        if (sleepFlag) {
            try {
                Thread.sleep(RandomUtils.nextInt(5, 15) * 1000);
            } catch (InterruptedException e) {
            }
        }
    }

    public static void sleep5() {
        if (sleepFlag) {
            try {
                Thread.sleep(RandomUtils.nextInt(0, 10) * 1000);
            } catch (InterruptedException e) {
            }
        }
    }
}
