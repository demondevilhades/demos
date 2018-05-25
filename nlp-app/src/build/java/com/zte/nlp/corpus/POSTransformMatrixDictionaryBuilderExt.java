package com.zte.nlp.corpus;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 0 - (transform_matrix.dic 2 transform_matrix.dic)
 */
public class POSTransformMatrixDictionaryBuilderExt extends POSTransformMatrixDictionaryBuilder {

    private String name = "transform_matrix.dic";

    public void readAndWrite(String inputPath, String outputPath) throws IOException {
        try (com.zte.nlp.io.ArrayDataInputStream adis = new com.zte.nlp.io.ArrayDataInputStream(
                new BufferedInputStream(new FileInputStream(new File(inputPath, name))));) {
            freqs = adis.readIntArray();
            matrix = new int[adis.readInt()][];
            for (int i = 0; i < matrix.length; i++) {
                matrix[i] = adis.readIntArray();
            }
            totalFreq = adis.readInt();
        }
        try (test.nlp.io.ArrayDataOutputStream ados = new test.nlp.io.ArrayDataOutputStream(new BufferedOutputStream(
                new FileOutputStream(new File(outputPath, name))));) {
            ados.writeIntArray(freqs);
            ados.writeInt(matrix.length);
            for (int i = 0; i < matrix.length; i++) {
                ados.writeIntArray(matrix[i]);
            }
            ados.writeInt(totalFreq);
        }
    }

    public static void main(String[] args) throws Exception {
        POSTransformMatrixDictionaryBuilderExt ext = new POSTransformMatrixDictionaryBuilderExt();
        ext.readAndWrite(CommonBuilderExt.INPUT_LIBRARY_PATH + "nr/", CommonBuilderExt.OUTPUT_LIBRARY_PATH + "nr/");
    }
}
