package com.zte.nlp.corpus;

import java.io.File;

/**
 * 2 - ns & transform_matrix (ns.txt 2 ns.dic) (transform_matrix.dic)
 */
public class PlaceDictionaryBuilderExt implements CommonBuilderExt<test.nlp.dict.ns.NS> {

    private String nsInputName = "ns.txt";
    private String nsOutputName = "ns.dic";

    public void readAndWrite(String inputPath, String outputPath) throws Exception {
        saveBinFile(new File(outputPath, nsOutputName), readTxt(inputPath + nsInputName));

        TransformMatrixDictionaryBuilder<test.nlp.dict.ns.NS> nsTransformDictionary = new TransformMatrixDictionaryBuilder<test.nlp.dict.ns.NS>(
                test.nlp.dict.ns.NS.class, test.nlp.dict.ns.NS.S.ordinal() + 1, test.nlp.dict.ns.NS.S.ordinal(),
                test.nlp.dict.ns.NS.A.ordinal());
        nsTransformDictionary.save(new File(outputPath));
    }

    @Override
    public test.nlp.dict.ns.NS getVByItem(String item) {
        return test.nlp.dict.ns.NS.valueOf(item);
    }

    public static void main(String[] args) throws Exception {
        PlaceDictionaryBuilderExt ext = new PlaceDictionaryBuilderExt();
        ext.readAndWrite(INPUT_LIBRARY_PATH + "ns/", OUTPUT_LIBRARY_PATH + "ns/");
    }
}
