package com.hades.zookeepertest.id;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BatchIdBuilderTest {

    private BatchIdBuilder builder;

    @Before
    public void before() {
        builder = new BatchIdBuilder("id");
        builder.start();
    }

    @After
    public void after() {
        builder.stop();
    }

    @Test
    public void testGetId() throws Exception {
        System.out.println("BatchIdBuilderTest.testGetId()");
        Thread.sleep(1000);
        int len = 1000;
        int nullNum = 0;
        String id;

        List<String> list = new ArrayList<String>(len);
        long currentTimeMillis = System.currentTimeMillis();
        for (int i = 0; i < len; i++) {
            id = builder.getId();
            list.add(id);
        }
        System.out.println(System.currentTimeMillis() - currentTimeMillis);
        for (int i = 0; i < len; i++) {
            id = list.get(i);
            nullNum += id == null ? 1 : 0;
        }
        System.out.println("nullNum = " + nullNum);
    }

    @Test
    public void testGetIdNotNull() throws Exception {
        System.out.println("BatchIdBuilderTest.testGetIdNotNull()");
        Thread.sleep(1000);
        int len = 1000;
        int nullNum = 0;
        String id;

        List<String> list = new ArrayList<String>(len);
        long currentTimeMillis = System.currentTimeMillis();
        for (int i = 0; i < len; i++) {
            id = builder.getIdNotNull();
            list.add(id);
        }
        System.out.println(System.currentTimeMillis() - currentTimeMillis);
        for (int i = 0; i < len; i++) {
            id = list.get(i);
            nullNum += id == null ? 1 : 0;
        }
        System.out.println("nullNum = " + nullNum);
    }
}
