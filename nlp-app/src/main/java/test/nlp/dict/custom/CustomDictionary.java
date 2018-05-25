package test.nlp.dict.custom;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Maps;
import test.nlp.Constant;
import test.nlp.common.Attribute;
import test.nlp.common.Sentence;
import test.nlp.common.Vertex;
import test.nlp.dict.SegmentDictionary;
import test.nlp.dict.pos.POS;
import test.nlp.dict.pos.POSAttribute;
import test.nlp.io.DictionaryReader;
import test.nlp.segment.Graph;
import test.nlp.trie.DoubleArrayTrie;
import test.nlp.trie.IHit;
import test.nlp.trie.ITrie;
import test.nlp.trie.Trie;
import test.nlp.util.IOUtil;
import test.nlp.util.SegmentUtil;

/**
 * 用户自定义词典
 */
public class CustomDictionary extends SegmentDictionary {

    private static final String FILE_CUSTOM_DIC = "custom.dic";

    private static volatile CustomDictionary instance;

    /**
     * 领域用户词典
     */
    private Map<String, ITrie<Attribute<POS>>> domainDicts;

    private File path;

    public static CustomDictionary getInstance() {
        if (instance == null) {
            synchronized (CustomDictionary.class) {
                if (instance == null) {
                    instance = new CustomDictionary();
                }
            }
        }
        return instance;
    }

    public void segment(Sentence sentence, Graph graph, String domain) {
        if (domain != null) {
            ITrie<Attribute<POS>> domainDict = domainDicts.get(domain);
            if (domainDict != null) {
                segment(sentence, graph, domainDict);
            }
        }
        super.segment(sentence, graph, dict);
    }

    public void reload(String domain) throws IOException, ClassNotFoundException {
        ITrie<Attribute<POS>> dict = domainDicts.get(domain);
        ITrie<Attribute<POS>> newDict = new Trie<>();
        newDict.read(new File(libraryPath + Constant.PATH_CUSTOM + File.separator + domain, FILE_CUSTOM_DIC));
        domainDicts.put(domain, newDict);
        if (dict != null) {
            dict.clear();
        }
    }

    /**
     * 读取用户词典
     * 
     * @param customDir
     *            用户词典路径
     */
    public void read(File path) {
        if (!path.isDirectory()) {
            path.mkdirs();
        }

        try {
            super.read(new File(path, FILE_CUSTOM_DIC));
            initDomainDicts(path);
            LOG.info("Load custom dictionary ok.");
        } catch (Exception e) {
            throw new RuntimeException("Load custom dictionary error.", e);
        }
    }

    /**
     * 保存指定领域的词库
     * 
     * @param domain
     * @throws IOException
     */
    public void write(String domain) throws IOException {
        ITrie<Attribute<POS>> domainDict = domainDicts.get(domain);
        if (domainDict != null) {
            domainDict.write(new File(libraryPath + Constant.PATH_CUSTOM + File.separator + domain, FILE_CUSTOM_DIC));
        }
    }

    @Override
    public void write(File path) throws IOException {
        this.dict.write(new File(path, FILE_CUSTOM_DIC));
        for (Map.Entry<String, ITrie<Attribute<POS>>> entry : domainDicts.entrySet()) {
            entry.getValue().write(new File(path + File.separator + entry.getKey(), FILE_CUSTOM_DIC));
        }
    }

    public boolean contains(String word, String domain) {
        if (!super.contains(word)) {
            return get(word, domain) != null;
        }
        return true;

    }

    /**
     * 新词一个领域词
     * 
     * @param word
     * @param pos
     * @param freq
     * @param domain
     */
    public void addWord(String word, POS pos, int freq, String domain) {
        ITrie<Attribute<POS>> domainDict = domainDicts.get(domain);
        if (domainDict == null) {
            domainDict = new Trie<Attribute<POS>>();
            domainDicts.put(domain, domainDict);
        }
        domainDict.add(word, new POSAttribute(pos, freq));
    }

    public Attribute<POS> get(String word, String domain) {
        ITrie<Attribute<POS>> domainDict = domainDicts.get(domain);
        if (domainDict != null) {
            return domainDict.get(word);
        }
        return null;
    }

    /**
     * 删除一个领域词
     * 
     * @param word
     * @param domain
     */
    public void removeWord(String word, String domain) {
        ITrie<Attribute<POS>> domainDict = domainDicts.get(domain);
        if (domainDict != null) {
            domainDict.remove(word);
        }
    }

    /**
     * 正向最大匹配合并分词结果
     * 
     * @param terms
     * @param domain
     * @return
     */
    public void fmm(Vertex[] vertexes, String domain) {

        if (domain != null && domainDicts.containsKey(domain)) {
            SegmentUtil.fmm(domainDicts.get(domain), vertexes, new AttributeHit(vertexes));
        }

        SegmentUtil.fmm(dict, vertexes, new AttributeHit(vertexes));

    }

    @Override
    public void create(File source, File desti) throws IOException {
        ITrie<Attribute<POS>> dict = new Trie<Attribute<POS>>();
        if (source.isDirectory()) {
            dict.create(readSourceFiles(source));
        } else {
            dict.create(newDictionaryReader().read(source, "\\s+"));
        }
        dict.write(desti);
    }

    private TreeMap<String, Attribute<POS>> readSourceFiles(File customDictDir) throws IOException {

        File[] sourceFiles = IOUtil.listTXTFiles(customDictDir);
        TreeMap<String, Attribute<POS>> map = Maps.newTreeMap();
        if (ArrayUtils.isNotEmpty(sourceFiles)) {
            DictionaryReader<POS> dictionaryReader = newDictionaryReader();
            for (File file : sourceFiles) {
                map.putAll(dictionaryReader.read(file, "\\s+"));
            }
        }
        return map;
    }

    private void initDomainDicts(File customDictDir) throws IOException, ClassNotFoundException {
        this.domainDicts = Maps.newHashMap();
        File[] domainDirs = IOUtil.listChildDirs(customDictDir);
        if (ArrayUtils.isEmpty(domainDirs)) {
            return;
        }

        for (File domainDir : domainDirs) {
            ITrie<Attribute<POS>> domainDict = new Trie<Attribute<POS>>();
            File domainDictFile = new File(domainDir, FILE_CUSTOM_DIC);
            if (domainDictFile.isFile()) {
                domainDict.read(domainDictFile);
            } else {
                TreeMap<String, Attribute<POS>> posAttrs = readSourceFiles(domainDir);
                domainDict.create(posAttrs);
                domainDict.write(domainDictFile);
            }
            domainDicts.put(domainDir.getName(), domainDict);
        }
    }

    private CustomDictionary() {
        super(new DoubleArrayTrie<Attribute<POS>>());
        this.path = new File(libraryPath, Constant.PATH_CUSTOM);
        read(this.path);
    }

    private static class AttributeHit implements IHit<Attribute<POS>> {

        private Vertex[] vertexes;

        public AttributeHit(Vertex[] vertexes) {
            this.vertexes = vertexes;
        }

        @Override
        public void hit(int begin, int end, Attribute<POS> value) {
            vertexes[begin].setPosAttr(value);
            vertexes[begin].setPos(value.getPossibleAttr());
            if (end > begin) {
                StringBuilder merge = new StringBuilder(vertexes[begin].getWord());
                for (int i = begin + 1; i <= end; i++) {
                    merge.append(vertexes[i].getWord());
                    vertexes[i] = null;
                }
                vertexes[begin].setWord(merge.toString());
            }
        }

        @Override
        public void notHit(int begin) {
        }
    }
}
