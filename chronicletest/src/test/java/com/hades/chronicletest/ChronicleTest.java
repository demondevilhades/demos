package com.hades.chronicletest;

import java.io.File;
import java.io.Serializable;

import net.openhft.chronicle.map.ChronicleMap;
import net.openhft.chronicle.map.ChronicleMapBuilder;

import org.junit.Test;

/**
 * TODO
 * 
 * @author HaDeS
 */
@SuppressWarnings("serial")
public class ChronicleTest implements Serializable {
    private final int maxChunksPerEntry = 1;
    private final int actualChunkSize = 500;
    private final int actualSegments = 1;
    private final long actualChunksPerSegmentTier = 1;
    private final long entriesPerSegment = 1;
    private final long entries = 1;
    private final File file = new File("D:/ChronicleMap.txt");

    @Test
    public void test1() throws Exception {
        ChronicleMap<String, TestObject> chronicleMap = ChronicleMapBuilder.of(String.class, TestObject.class)
                .entries(entries).actualChunkSize(actualChunkSize)
                .actualChunksPerSegmentTier(actualChunksPerSegmentTier).actualSegments(actualSegments)
                .entriesPerSegment(entriesPerSegment).maxChunksPerEntry(maxChunksPerEntry)
                .createOrRecoverPersistedTo(file);

        TestObject testObject = new TestObject();
        testObject.a = 1;
        testObject.b = 2;
        System.out.println(chronicleMap.offHeapMemoryUsed());
        chronicleMap.put("test", testObject);
        System.out.println(chronicleMap.offHeapMemoryUsed());

        chronicleMap.close();
    }

    @Test
    public void test2() throws Exception {
        File file = new File("D:/ChronicleMap.txt");
        ChronicleMap<String, TestObject> chronicleMap = ChronicleMapBuilder.of(String.class, TestObject.class)
                .createOrRecoverPersistedTo(file);
        System.out.println(chronicleMap.containsKey("test"));
        System.out.println(chronicleMap.get("test").b);

        chronicleMap.close();
    }

    class TestObject implements Serializable {
        int a;
        long b;
    }
}
