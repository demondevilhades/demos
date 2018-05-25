package test.nlp.io;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 添加数组类型相关方法
 */
public class ArrayDataInputStream extends DataInputStream {

    public ArrayDataInputStream(InputStream in) {
        super(in);
    }

    public int[] readIntArray() throws IOException {
        int[] array = new int[readInt()];
        for (int i = 0; i < array.length; i++) {
            array[i] = readInt();
        }
        return array;
    }
}
