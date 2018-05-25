package test.nlp.dict.synonym;

import static com.google.common.collect.Maps.newHashMap;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;

import test.nlp.Constant;
import test.nlp.common.Term;
import test.nlp.dict.Dictionary;
import test.nlp.io.FileReader;
import test.nlp.util.IOUtil;

import com.google.common.collect.Lists;

/**
 * 同义词典
 */
public class SynonymDictionary extends Dictionary {

    private static volatile SynonymDictionary instance;

    /**
     * 核心同义词库
     */
    private Group core;

    /**
     * 领域词义词库
     */
    private Map<String, Group> domains;

    public static SynonymDictionary getInstance() {
        if (instance == null) {
            synchronized (SynonymDictionary.class) {
                if (instance == null) {
                    instance = new SynonymDictionary();
                }
            }
        }
        return instance;
    }

    private SynonymDictionary() {
        this.core = new Group();
        this.domains = newHashMap();
        try {
            read(new File(libraryPath, Constant.PATH_SYNONYM));
        } catch (IOException e) {
            throw new RuntimeException("Init SynonymDictionary error.", e);
        }
    }

    @Override
    public void read(File path) throws IOException {
        File[] synonymFiles = IOUtil.listTXTFiles(path);
        if (ArrayUtils.isEmpty(synonymFiles)) {
            return;
        }

        List<String[]> synonymGroups = new SynonymDictionaryReader().read(path);

        for (int i = 0; i < synonymGroups.size(); i++) {
            this.core.add(String.valueOf(i), synonymGroups.get(i));
        }
    }

    @Override
    public void write(File path) throws IOException {
        throw new UnsupportedOperationException();
    }

    public void add(String domain, String index, String[] synonymGroup) {
        Group group = domains.get(domain);
        if (group == null) {
            group = new Group();
            domains.put(domain, group);
        }
        group.add(index, synonymGroup);
    }

    public void remove(String domain, String index) {
        Group group = domains.get(domain);
        if (group == null) {
            return;
        }
        group.remove(index);
    }

    /**
     * 扩展同义词
     * 
     * @param terms
     */
    public void expand(String domain, LinkedList<Term> terms) {
        for (Term term : terms) {
            term.setSynonyms(getSynonymWords(domain, term.getWord()));
        }
    }

    /**
     * 返回同义词组信息，包括group_id及其同义词信息
     * 
     * @param domain
     * @param word
     * @return
     */
    public Pair<String, String[]> getSynonymGroup(String domain, String word) {
        Pair<String, String[]> result = null;
        Group group = domains.get(domain);
        if (group != null) {
            result = group.getSynonymGroup(word);
        }
        if (result == null) {
            result = core.getSynonymGroup(word);

        }
        return result;
    }

    public String[] getSynonymWords(String domain, String word) {
        String[] synonymWords = null;
        Group group = domains.get(domain);
        if (group != null) {
            synonymWords = group.getSynonymWords(word);
        }
        if (synonymWords == null) {
            synonymWords = core.getSynonymWords(word);
        }
        return synonymWords;
    }

    @Override
    protected void read(InputStream in) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void write(OutputStream out) throws IOException {
        throw new UnsupportedOperationException();
    }

    private static class SynonymDictionaryReader extends FileReader<List<String[]>> {

        private List<String[]> synonymGroups = Lists.newArrayList();

        public List<String[]> read(File file) throws IOException {
            File[] sourceFiles = IOUtil.listTXTFiles(file);
            if (ArrayUtils.isEmpty(sourceFiles)) {
                return newResult();
            }

            for (File sourceFile : sourceFiles) {
                synonymGroups.addAll(super.read(sourceFile, Constant.COMMA_SEPARATOR));
            }
            return synonymGroups;
        }

        @Override
        protected List<String[]> newResult() {
            return Lists.newArrayList();
        }

        @Override
        protected void processTokens(String original, String[] tokens, List<String[]> result) {
            if (tokens.length < 2) {
                return;
            }

            result.add(tokens);
        }
    }

    /**
     * 同义词组信息
     */
    public static class Group {
        /**
         * 同义词组：key：索引 value：同义词组
         */
        private Map<String, String[]> synonymGroups;

        /**
         * 同义词编号信息：key：词 value：索引
         */
        private Map<String, String> indexes;

        public Group(List<String[]> synonymGroups) {
            this();
            for (int i = 0; i < synonymGroups.size(); i++) {
                String index = String.valueOf(i);
                for (String word : synonymGroups.get(i)) {
                    indexes.put(word, index);
                }
                this.synonymGroups.put(index, synonymGroups.get(i));
            }
        }

        public Group() {
            this.synonymGroups = newHashMap();
            this.indexes = newHashMap();
        }

        /**
         * 新增一组同义词
         * 
         * @param index
         * @param synonymGroup
         */
        public void add(String index, String[] synonymGroup) {
            remove(index);
            synonymGroups.put(index, synonymGroup);
            for (int i = 0; i < synonymGroup.length; i++) {
                indexes.put(synonymGroup[i], index);
            }
        }

        /**
         * 删除同义词
         * 
         * @param index
         */
        public void remove(String index) {
            String[] synonymGroup = synonymGroups.remove(index);
            if (synonymGroup == null) {
                return;
            }
            for (int i = 0; i < synonymGroup.length; i++) {
                indexes.remove(synonymGroup[i]);
            }
        }

        /**
         * 获取同义词
         * 
         * @param word
         * @return
         */
        public String[] getSynonymWords(String word) {
            String index = indexes.get(word);
            if (index == null) {
                return null;
            }
            return synonymGroups.get(index);
        }

        public Pair<String, String[]> getSynonymGroup(String word) {
            String index = indexes.get(word);
            if (index == null) {
                return null;
            }
            return Pair.of(index, synonymGroups.get(index));
        }
    }
}
