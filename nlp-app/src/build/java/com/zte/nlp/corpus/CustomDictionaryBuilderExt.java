package com.zte.nlp.corpus;

import java.io.File;

import test.nlp.dict.pos.POS;

/**
 * 4 - custom (custom.txt 2 custom.dic)
 */
public class CustomDictionaryBuilderExt implements CommonBuilderExt<test.nlp.dict.pos.POS> {

    private String customInputName = "custom.txt";
    private String customOutputName = "custom.dic";

    public void readAndWrite(String inputPath, String outputPath) throws Exception {
        saveBinFile(new File(outputPath, customOutputName), readTxt(inputPath + customInputName));
    }

    @Override
    public POS getVByItem(String item) {
        // TODO Auto-generated method stub
        return null;
    }

    public static void main(String[] args) throws Exception {
        CustomDictionaryBuilderExt ext = new CustomDictionaryBuilderExt();
        ext.readAndWrite(INPUT_LIBRARY_PATH + "custom/", OUTPUT_LIBRARY_PATH + "custom/");
    }
}
