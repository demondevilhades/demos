package com.zte.nlp.corpus;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.TreeMap;

import test.nlp.util.IOUtil;

import com.google.common.io.Files;

public interface CommonBuilderExt<V extends Enum<V>> {
    
    public static final String INPUT_LIBRARY_PATH = CommonBuilderExt.class.getClassLoader().getResource("library/").getPath();
    public static final String OUTPUT_LIBRARY_PATH = "src/main/resources/library/";

    default TreeMap<String, test.nlp.common.Attribute<V>> readTxt(String txtFile) throws IOException {
        TreeMap<String, test.nlp.common.Attribute<V>> attributeMap = new TreeMap<>();
        try (BufferedReader writer = Files.newReader(new File(txtFile), StandardCharsets.UTF_8);) {
            String line = writer.readLine();
            while (line != null) {
                String[] split = line.split("\t");
                int size = (split.length - 1) / 2;
                V item;
                int freq;
                test.nlp.common.Attribute<V> attribute = new test.nlp.common.Attribute<V>(size);
                for (int i = 0; i < size; i++) {
                    item = getVByItem(split[i * 2 + 1]);
                    freq = Integer.parseInt(split[i * 2 + 2]);
                    attribute.addItem(i, item, freq);
                }
                attributeMap.put(split[0], attribute);

                line = writer.readLine();
            }
        }
        return attributeMap;
    }

    V getVByItem(String item);

    default void saveBinFile(File binFile, TreeMap<String, test.nlp.common.Attribute<V>> attributeMap)
            throws IOException {
        test.nlp.trie.ITrie<test.nlp.common.Attribute<V>> trie = new test.nlp.trie.DoubleArrayTrie<test.nlp.common.Attribute<V>>();
        trie.create(attributeMap);
        try (ObjectOutputStream out = IOUtil.getObjectOutputStream(binFile);) {
            saveBinFile(trie, out);
        }
    }

    default void saveBinFile(test.nlp.trie.ITrie<test.nlp.common.Attribute<V>> dict, ObjectOutputStream out)
            throws IOException {
        dict.write(out);
    }
}
