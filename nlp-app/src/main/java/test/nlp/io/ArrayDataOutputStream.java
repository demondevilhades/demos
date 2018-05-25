package test.nlp.io;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 增加数组的写入
 */
public class ArrayDataOutputStream extends DataOutputStream {

    public ArrayDataOutputStream(OutputStream out) {
        super(out);
    }

    /**
     * 写入整形数组
     * 
     * @param array
     */
    public void writeIntArray(int[] array) throws IOException {
        writeInt(array.length);
        for (int i = 0; i < array.length; i++) {
            writeInt(array[i]);
        }
    }
}
