package com.zte.nlp.corpus;

import java.io.File;

/**
 * 1 - nr (nr.txt 2 nr.dic) (transform_matrix.dic)
 */
public class PersonDictionaryBuilderExt implements CommonBuilderExt<test.nlp.dict.nr.NR> {

    private String nrInputName = "nr.txt";
    private String nrOutputName = "nr.dic";

    public void readAndWrite(String inputPath, String outputPath) throws Exception {
        saveBinFile(new File(outputPath, nrOutputName), readTxt(inputPath + nrInputName));

        DictionaryBuilder<test.nlp.dict.nr.NR> transformMatrixBuilder = new TransformMatrixDictionaryBuilder<test.nlp.dict.nr.NR>(
                test.nlp.dict.nr.NR.class, test.nlp.dict.nr.NR.Z.ordinal() + 1, test.nlp.dict.nr.NR.S.ordinal(),
                test.nlp.dict.nr.NR.A.ordinal());
        transformMatrixBuilder.save(new File(outputPath));
    }

    @Override
    public test.nlp.dict.nr.NR getVByItem(String item) {
        return test.nlp.dict.nr.NR.valueOf(item);
    }

    public static void main(String[] args) throws Exception {
        PersonDictionaryBuilderExt ext = new PersonDictionaryBuilderExt();
        ext.readAndWrite(INPUT_LIBRARY_PATH + "nr/", OUTPUT_LIBRARY_PATH + "nr/");
    }
}
