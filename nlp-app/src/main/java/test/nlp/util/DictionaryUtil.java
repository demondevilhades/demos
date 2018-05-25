package test.nlp.util;

import java.io.File;
import java.io.IOException;

import test.nlp.common.Attribute;
import test.nlp.dict.pos.POS;
import test.nlp.dict.pos.POSAttribute;
import test.nlp.io.DictionaryReader;
import test.nlp.trie.DoubleArrayTrie;
import test.nlp.trie.ITrie;

/**
 * 词典工具类
 */
public class DictionaryUtil {

    /**
     * 创建核心词典
     * 
     * @param sourceFile
     * @param destiFile
     * @throws IOException
     */
    public static void createCoreDictionary(File sourceFile, File destiFile) throws IOException {
        ITrie<Attribute<POS>> dict = new DoubleArrayTrie<Attribute<POS>>();
        dict.create(new DictionaryReader<POS>(POS.class) {

            @Override
            protected Attribute<POS> newAttribute(int size) {
                return new POSAttribute(size);
            }
        }.read(sourceFile, "\\s+"));
        dict.write(destiFile);
    }

    public static void main(String[] args) throws IOException {
        // createCoreDictionary(new File("library/core.txt"), new
        // File("library/core.dic"));
        createCoreDictionary(new File("library/spellcheck/spellcheck.txt"), new File(
                "library/spellcheck/spellcheck.dic"));
    }
}
