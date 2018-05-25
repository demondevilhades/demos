package test.nlp.dict;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import test.nlp.dict.pos.POS;
import test.nlp.io.ArrayDataInputStream;
import test.nlp.io.ArrayDataOutputStream;

import com.google.common.io.Files;

public class TransformMatrixDictionary<E extends Enum<E>> extends Dictionary {

    /**
     * 存储频率
     */
    protected int[] freqs;

    /**
     * 转移矩阵
     */
    protected int[][] matrix;

    /**
     * 总频率
     */
    protected int totalFreq;

    public int getFreq(Enum<E> item) {
        return freqs[item.ordinal()];
    }

    public int getTransitionFreq(Enum<E> fromItem, Enum<E> toItem) {
        return matrix[fromItem.ordinal()][toItem.ordinal()];
    }

    public int getTotalFreq() {
        return totalFreq;
    }

    @Override
    public void read(InputStream in) throws IOException {
        try (ArrayDataInputStream dis = (in instanceof BufferedInputStream) ? new ArrayDataInputStream(in)
                : new ArrayDataInputStream(new BufferedInputStream(in))) {
            freqs = dis.readIntArray();
            this.matrix = new int[dis.readInt()][];
            for (int i = 0; i < matrix.length; i++) {
                matrix[i] = dis.readIntArray();
            }
            totalFreq = dis.readInt();
        }
    }

    @Override
    public void write(OutputStream out) throws IOException {
        try (ArrayDataOutputStream dos = (out instanceof BufferedOutputStream) ? new ArrayDataOutputStream(out)
                : new ArrayDataOutputStream(new BufferedOutputStream(out))) {
            dos.writeIntArray(freqs);
            dos.writeInt(matrix.length);
            for (int i = 0; i < matrix.length; i++) {
                dos.writeIntArray(matrix[i]);
            }
            dos.writeInt(totalFreq);
        }
    }

    public static void main(String[] args) throws Exception {
        TransformMatrixDictionary<POS> transformMatrixDictionary = new TransformMatrixDictionary<POS>();
        try (InputStream in = new FileInputStream(new File("library/transform_matrix.dic"));) {
            transformMatrixDictionary.read(in);
            writeFreqs(transformMatrixDictionary);
            writeMatrix(transformMatrixDictionary);
        }
    }

    private static void writeMatrix(TransformMatrixDictionary<POS> transformMatrixDictionary)
            throws FileNotFoundException, IOException {
        try (BufferedWriter writer = Files.newWriter(new File("library/pos/transform_matrix.txt"),
                StandardCharsets.UTF_8);) {
            POS[] poses = POS.values();
            int[][] matrix = transformMatrixDictionary.matrix;
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    writer.write(poses[i].toString() + "@" + poses[j].toString() + "\t" + matrix[i][j]);
                    writer.newLine();
                }
            }
        }
    }

    private static void writeFreqs(TransformMatrixDictionary<POS> transformMatrixDictionary)
            throws FileNotFoundException, IOException {
        try (BufferedWriter writer = Files.newWriter(new File("library/pos/freqs.txt"), StandardCharsets.UTF_8);) {
            POS[] poses = POS.values();
            for (int i = 0; i < transformMatrixDictionary.freqs.length; i++) {
                writer.write(poses[i].toString() + "\t" + transformMatrixDictionary.freqs[i]);
                writer.newLine();
            }
        }
    }
}
