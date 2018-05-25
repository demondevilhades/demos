package test.nlp.dict;

import static com.google.common.collect.Maps.newHashMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import test.nlp.Constant;
import test.nlp.common.Attribute;
import test.nlp.dict.pos.POS;
import test.nlp.trie.DoubleArrayTrie;
import test.nlp.trie.ITrie;

/**
 * 核心词典
 */
public class CoreDictionary extends SegmentDictionary {

    public static final int TOTAL_FREQ = 300000;

    public static final String FILE_CORE_DIC = "core.dic";

    private static volatile CoreDictionary instance;

    private Map<String, Attribute<POS>> unknownPOSAttrs;

    public static CoreDictionary getInstance() {
        if (instance == null) {
            synchronized (CoreDictionary.class) {
                if (instance == null) {
                    instance = new CoreDictionary();
                }
            }
        }
        return instance;
    }

    public Attribute<POS> getUnknownPOSAttr(String unknownWord, POS[] allPOS) {
        return unknownPOSAttrs.get(unknownWord).clone(allPOS);
    }

    @Override
    public void create(File source, File desti) throws IOException {
        ITrie<Attribute<POS>> dict = new DoubleArrayTrie<Attribute<POS>>();
        dict.create(newDictionaryReader().read(source, "\\s+"));
        dict.write(desti);
    }

    private CoreDictionary() {
        super(new DoubleArrayTrie<Attribute<POS>>());
        try (InputStream in = new FileInputStream(new File(libraryPath, FILE_CORE_DIC));) {
            read(in);
        } catch (Exception e) {
            throw new RuntimeException("Load core.dic error.", e);
        }
        init();
    }

    private void init() {
        this.unknownPOSAttrs = newHashMap();
        unknownPOSAttrs.put(Constant.SENTENCE_BEGIN, get(Constant.SENTENCE_BEGIN));
        unknownPOSAttrs.put(Constant.SENTENCE_END, get(Constant.SENTENCE_END));
        unknownPOSAttrs.put(Constant.UNKNOWN_LETTER, get(Constant.UNKNOWN_LETTER));
        unknownPOSAttrs.put(Constant.UNKNOWN_NUM, get(Constant.UNKNOWN_NUM));
        unknownPOSAttrs.put(Constant.UNKNOWN_PERSON, get(Constant.UNKNOWN_PERSON));
        unknownPOSAttrs.put(Constant.UNKNOWN_PUNC, get(Constant.UNKNOWN_PUNC));
        unknownPOSAttrs.put(Constant.UNKNOWN_SPACE, get(Constant.UNKNOWN_SPACE));
        unknownPOSAttrs.put(Constant.UNKNOWN_TIME, get(Constant.UNKNOWN_TIME));
    }
}
