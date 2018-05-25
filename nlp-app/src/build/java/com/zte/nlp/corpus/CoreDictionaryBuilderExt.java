package com.zte.nlp.corpus;

import java.io.File;

import test.nlp.dict.pos.POS;

/**
 * 3 - core (core.txt 2 core.dic)
 */
public class CoreDictionaryBuilderExt implements CommonBuilderExt<test.nlp.dict.pos.POS> {

    private String coreInputName = "core.txt";
    private String coreOutputName = "core.dic";

    public void readAndWrite(String inputPath, String outputPath) throws Exception {
        saveBinFile(new File(outputPath, coreOutputName), readTxt(inputPath + coreInputName));
        
        DictionaryBuilder<test.nlp.dict.pos.POS> transformMatrixBuilder = new TransformMatrixDictionaryBuilder<test.nlp.dict.pos.POS>(
                test.nlp.dict.pos.POS.class, test.nlp.dict.pos.POS.end.ordinal() + 1, test.nlp.dict.pos.POS.begin.ordinal(),
                test.nlp.dict.pos.POS.end.ordinal());
        transformMatrixBuilder.save(new File(outputPath));
    }
    
    @Override
    public POS getVByItem(String item) {
        return POS.valueOf(item);
    }

    public static void main(String[] args) throws Exception {
        CoreDictionaryBuilderExt ext = new CoreDictionaryBuilderExt();
        ext.readAndWrite(INPUT_LIBRARY_PATH, OUTPUT_LIBRARY_PATH);
    }
}
