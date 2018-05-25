package com.zte.nlp.corpus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.io.IOUtils;

/**
 * 5 - bigram (bigram.dic)
 */
public class BigramDictionaryBuilderExt {

    private String bigramInputName = "bigram.dic";
    private String bigramOutputName = "bigram.dic";

    public void readAndWrite(String inputPath, String outputPath) throws Exception {
        File outputFile = new File(outputPath, bigramOutputName);
        File inputFile = new File(inputPath, bigramInputName);
        try (FileInputStream fis = new FileInputStream(inputFile);
                FileOutputStream fos = new FileOutputStream(outputFile);) {
            IOUtils.copy(fis, fos);
        }
    }

    public static void main(String[] args) throws Exception {
        BigramDictionaryBuilderExt ext = new BigramDictionaryBuilderExt();
        ext.readAndWrite(CommonBuilderExt.INPUT_LIBRARY_PATH, CommonBuilderExt.OUTPUT_LIBRARY_PATH);
    }
}
