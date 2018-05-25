package test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import test.nlp.io.ArrayDataInputStream;

public class CheckDic {

    public void runTransformMatrix() {
        File file = new File("library", "transform_matrix.dic");
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        ArrayDataInputStream adis = null;
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            adis = new ArrayDataInputStream(bis);
            int[] freqs = adis.readIntArray();
            System.out.println("freqs=" + Arrays.toString(freqs));
            int matrixLength = adis.readInt();
            int[][] matrix = new int[matrixLength][];
            System.out.println("matrix=");
            for (int i = 0; i < matrix.length; i++) {
                matrix[i] = adis.readIntArray();
                System.out.println(Arrays.toString(matrix[i]));
            }
            int totalFreq = adis.readInt();

            System.out.println("totalFreq=" + totalFreq);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (adis != null) {
                    adis.close();
                }
                if (bis != null) {
                    bis.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new CheckDic().runTransformMatrix();
    }
}
