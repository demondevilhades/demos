package test.nlp.newword;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.primitives.Doubles;
import test.nlp.common.Term;
import test.nlp.dict.CoreDictionary;
import test.nlp.ltp.LTPParser;
import test.nlp.newword.filter.CandidateWordFilters;
import test.nlp.segment.IWordSegmenter;
import test.nlp.trie.ITrie;
import test.nlp.trie.Trie;
import test.nlp.util.MathUtil;
import test.nlp.util.StringUtils;

/**
 * 新词提取类
 */
public class NewwordExtraction {

    /**
     * 提取词的默认最小长度
     */
    public static final int MIN_LENGTH = 2;

    /**
     * 提取词的默认最大长度
     */
    public static final int MAX_LENGTH = 4;

    /**
     * 默认词频阈值
     */
    public static final int MIN_FREQ = 2;

    /**
     * 默认上下文熵阈值
     */
    public static final float ENTROPY_THRESHOLD = 1.5f;

    /**
     * 默认互信息阈值
     */
    public static final float MI_THRESHOLD = 6;

    /**
     * 需要提取的词的最大长度
     */
    private int maxLength;

    /**
     * 提取词的最小长度
     */
    private int minLength;

    /**
     * 提取词的最小词频
     */
    private int minFreq;

    /**
     * 上下文熵阈值
     */
    private double entropyThreshold;

    /**
     * 互信息阈值
     */
    private double miThreshold;

    private CandidateWordFilters filters;

    private IWordSegmenter segmenter;

    private ITrie<Integer> wordFreqs;

    private int totalFreq;

    private POSRecognition posRecognition;

    private Map<String, CandidateWord> candidateWords = newHashMap();

    public void add(String text) {
        List<CandidateWord> candidateWordList = getAllCandidateWords(text);
        for (CandidateWord temp : candidateWordList) {
            CandidateWord candidateWord = candidateWords.get(temp.getWord());
            if (candidateWord == null) {
                candidateWord = temp;
                candidateWords.put(temp.getWord(), temp);
            } else {
                candidateWord.addTermCombines(temp.getTermCombines());
                candidateWord.addFreq(temp.getFreq());
                candidateWord.addLeftAdjacents(temp.getLeftAdjacents());
                candidateWord.addRightAdjacents(temp.getRightAdjacents());
            }
        }
    }

    public List<CandidateWord> extract() {
        List<CandidateWord> result = Lists.newArrayList(candidateWords.values());
        Iterator<CandidateWord> iterator = result.iterator();
        while (iterator.hasNext()) {
            CandidateWord candidateWord = iterator.next();
            if (!filters.isAccept(candidateWord)) {
                iterator.remove();
                continue;
            }

            candidateWord.setMi(calcMI(candidateWord.getWords()));
            if (filterCandidateWord(candidateWord)) {
                iterator.remove();
                continue;
            }

            candidateWord.setPOS(posRecognition.recognize(candidateWord));

        }
        Collections.sort(result, new Comparator<CandidateWord>() {
            public int compare(CandidateWord o1, CandidateWord o2) {
                return Doubles.compare(o2.getMi(), o1.getMi());
            };
        });
        return result;
    }

    /**
     * 获取所有的候选词
     * 
     * @param text
     * @return
     */
    public List<CandidateWord> getAllCandidateWords(String text) {
        if (org.apache.commons.lang3.StringUtils.isBlank(text)) {
            return Collections.emptyList();
        }
        LinkedList<Term> terms = segmenter.segment(text);
        Term[] termArray = terms.toArray(new Term[terms.size()]);
        addWordFreqs(termArray);
        int totalFreq = termArray.length;
        PATArray prefixPatArray = new PrefixPATArray(termArray);
        PATArray suffixPatArray = new SuffixPATArray(termArray);
        return findAllCandidateWords(prefixPatArray, suffixPatArray, wordFreqs, totalFreq);
    }

    public List<CandidateWord> getCandidatesByMI(String text, int topN) {
        List<CandidateWord> candidateWords = getAllCandidateWords(text);
        Collections.sort(candidateWords, new Comparator<CandidateWord>() {
            @Override
            public int compare(CandidateWord o1, CandidateWord o2) {
                return Doubles.compare(o2.getMi(), o1.getMi());
            }
        });
        if (candidateWords.size() <= topN) {
            return candidateWords;
        }
        return newArrayList(candidateWords.subList(0, topN));
    }

    private NewwordExtraction(IWordSegmenter segmenter) {
        this.segmenter = segmenter;
        this.wordFreqs = new Trie<>();
    }

    /**
     * 计算单个词的词频
     * 
     * @param terms
     */
    private void addWordFreqs(Term[] terms) {
        totalFreq += terms.length;
        for (int i = 0; i < terms.length; i++) {

            addWordFreq(terms[i].getWord());
        }

        for (int i = 0; i < terms.length - 1; i++) {
            addWordFreq(terms[i].getWord() + terms[i + 1].getWord());
        }
    }

    private void addWordFreq(String word) {
        if (word.length() > maxLength) {
            return;
        }
        Integer freq = wordFreqs.get(word);
        if (freq == null) {
            wordFreqs.add(word, 1);
        } else {
            wordFreqs.add(word, freq.intValue() + 1);
        }
    }

    /**
     * 找出所有的候选词
     * 
     * @return
     */
    private List<CandidateWord> findAllCandidateWords(PATArray prefixPatArray, PATArray suffixPatArray,
            ITrie<Integer> wordFreqs, int totalFreq) {
        List<CandidateWord> candidateWords = Lists.newArrayList();
        // 获取前缀PAT Array中满足条件的重复串
        Map<String, StringFrequency> prefixStringFrequencies = getPrefixStringFrequencies(prefixPatArray);
        // 获取前缀PAT Array中满足条件的重复串
        List<StringFrequency> suffixStringFrequencies = suffixPatArray.scanLCP(2, minLength, maxLength);
        for (int i = 0, size = suffixStringFrequencies.size(); i < size; i++) {
            StringFrequency suffixStringFrequency = suffixStringFrequencies.get(i);
            String word = suffixPatArray.getWord(suffixStringFrequency.getPosition());
            StringFrequency prefixStringFrequency = prefixStringFrequencies.get(word);
            if (prefixStringFrequency != null) {
                CandidateWord candidateWord = new CandidateWord(word,
                        prefixPatArray.getTermCombines(prefixStringFrequency));
                candidateWord.addLeftAdjacents(prefixPatArray.getAdjacents(prefixStringFrequency.getPosition()));
                candidateWord.addRightAdjacents(suffixPatArray.getAdjacents(suffixStringFrequency.getPosition()));
                candidateWords.add(candidateWord);
            }
        }
        return candidateWords;
    }

    /**
     * 计算互信息
     * 
     * @param stringFrequency
     * @param patArray
     * @param wordFreqs
     * @param totalFreq
     * @return
     */
    private double calcMI(String[] words) {
        double mi = 0;
        for (int i = 0; i < words.length - 1; i++) {
            mi += MathUtil.calcMI(wordFreqs.get(words[i]) + CoreDictionary.getInstance().getFreq(words[i]),
                    wordFreqs.get(words[i + 1]) + CoreDictionary.getInstance().getFreq(words[i + 1]),
                    wordFreqs.get(words[i] + words[i + 1]), totalFreq + CoreDictionary.TOTAL_FREQ);
        }
        return mi / (words.length - 1);
    }

    /**
     * 获取前缀PAT ARRAY中满足条件的重复串
     * 
     * @param prefixPatArray
     * 
     * @return
     */
    private Map<String, StringFrequency> getPrefixStringFrequencies(PATArray prefixPatArray) {
        List<StringFrequency> stringFrequencies = prefixPatArray.scanLCP(2, minLength, maxLength);
        Map<String, StringFrequency> prefixStringFrequencyMap = Maps.newHashMap();
        for (StringFrequency stringFrequency : stringFrequencies) {
            String word = prefixPatArray.getWord(stringFrequency.getPosition());
            if (word.length() <= maxLength && StringUtils.isCJK(word)) {
                prefixStringFrequencyMap.put(word, stringFrequency);
            }
        }
        return prefixStringFrequencyMap;
    }

    /**
     * 根据阈值过滤候选词
     * 
     * @param candidateWord
     * @return
     */
    private boolean filterCandidateWord(CandidateWord candidateWord) {
        return candidateWord.getFreq() < minFreq || candidateWord.getMi() < miThreshold
                || candidateWord.getLe() < entropyThreshold || candidateWord.getRe() < entropyThreshold;
    }

    public static class Builder {

        private int maxLength = MAX_LENGTH;

        private int minFreq = MIN_FREQ;

        private int minLength = MIN_LENGTH;

        private double entropyThreshold = ENTROPY_THRESHOLD;

        private double miThreshold = MI_THRESHOLD;

        private CandidateWordFilters filters;

        private IWordSegmenter segmenter;

        private LTPParser ltpParser;

        public Builder(IWordSegmenter segmenter) {
            this.segmenter = segmenter;
        }

        public Builder maxLength(int maxLength) {
            this.maxLength = maxLength;
            return this;
        }

        public Builder minFreq(int minFreq) {
            this.minFreq = minFreq;
            return this;
        }

        public Builder minLength(int minLength) {
            this.minLength = minLength;
            return this;
        }

        public Builder entropyThreshold(double entropyThreshold) {
            this.entropyThreshold = entropyThreshold;
            return this;
        }

        public Builder miThreshold(double miThreshold) {
            this.miThreshold = miThreshold;
            return this;
        }

        public Builder filter(CandidateWordFilters filters) {
            this.filters = filters;
            return this;
        }

        public Builder ltpParser(LTPParser ltpParser) {
            this.ltpParser = ltpParser;
            return this;
        }

        public NewwordExtraction build() {
            NewwordExtraction newwordExtraction = new NewwordExtraction(segmenter);
            newwordExtraction.maxLength = this.maxLength;
            newwordExtraction.minFreq = this.minFreq;
            newwordExtraction.minLength = this.minLength;
            newwordExtraction.entropyThreshold = this.entropyThreshold;
            newwordExtraction.miThreshold = this.miThreshold;
            newwordExtraction.posRecognition = new POSRecognition(ltpParser);
            if (filters == null) {
                newwordExtraction.filters = new CandidateWordFilters();
            } else {
                newwordExtraction.filters = this.filters;
            }
            return newwordExtraction;
        }
    }
}