package com.hades.zookeepertest.id;

import org.junit.Test;

public class IdBuilderTest {

    @Test
    public void test() {
        IdBuilder idBuilder = new IdBuilder("id");
        idBuilder.start();
        String id = idBuilder.generateId();
        System.out.println(id);
        idBuilder.stop();
    }
}
