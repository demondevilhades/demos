package test.nlp.keyword;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import test.nlp.common.Term;
import test.nlp.dict.pos.POS;
import test.nlp.dict.stopword.StopwordDictionary;
import test.nlp.keyword.Keyword.WeightComparator;
import test.nlp.segment.IWordSegmenter;
import test.nlp.util.TopNTreeSet;

/**
 * 关键词提取
 */
public class KeywordExtraction {

    public static final int MIN_WORD_LENGTH = 2;

    public static final int MIN_FREQ = 2;

    private int minWordLength = MIN_WORD_LENGTH;

    private int minFreq = MIN_FREQ;

    private IDF idf = DefaultIDF.INSTANCE;

    private IWordSegmenter segmenter;

    private StopwordDictionary stopwordDictionary;

    private Map<POS, Double> posWeights;

    /**
     * 提取关键词
     * 
     * @param text
     * @param topN
     * @return
     */
    public TreeSet<Keyword> extract(String text, int topN) {
        return extract(new String[] { text }, new double[] { 1 }, topN);
    }

    /**
     * 提取关键词
     * 
     * @param textArray
     *            文本数组
     * @param weights
     *            各文本权重
     * @param topN
     *            取前N个关键词
     * @return
     */
    public TreeSet<Keyword> extract(String[] textArray, double[] weights, int topN) {
        Map<String, Stats> wordStats = newHashMap();
        for (int i = 0; i < textArray.length; i++) {
            extract(textArray[i], weights[i], wordStats);
        }

        return getResult(topN, wordStats);
    }

    /**
     * 抽取关键词
     * 
     * @param terms
     *            文本切分结果
     * @param topN
     * @return
     */
    public TreeSet<Keyword> extract(LinkedList<Term> terms, int topN) {
        Map<String, Stats> wordStats = newHashMap();
        extract(terms, 1, wordStats);
        return getResult(topN, wordStats);
    }

    public void setMinWordLength(int minWordLength) {
        this.minWordLength = minWordLength;
    }

    public void setIdf(IDF idf) {
        this.idf = idf;
    }

    public void addPOS(POS pos, double weight) {
        if (posWeights == null) {
            posWeights = newHashMap();
        }
        posWeights.put(pos, weight);
    }

    /**
     * 提取文本中的关键词
     * 
     * @param text
     *            输入文本
     * @param weight
     *            初始权重
     * @param keywords
     *            关键词信息
     * @throws IOException
     */
    private void extract(String text, double weight, Map<String, Stats> wordStats) {

        if (org.apache.commons.lang3.StringUtils.isBlank(text)) {
            return;
        }

        LinkedList<Term> terms = segmenter.segment(text);
        extract(terms, weight, wordStats);
    }

    private void extract(LinkedList<Term> terms, double weight, Map<String, Stats> wordStats) {
        stopwordDictionary.filter(terms);
        if (terms.isEmpty()) {
            return;
        }
        double lengthNorm = Math.sqrt(terms.size());
        for (Term term : terms) {
            // 过滤词长小于指定值的词
            if (term.getWord().length() < minWordLength || term.getPos() == null) {
                continue;
            }

            double posWeight = getWeight(term.getPos());
            Stats stats = wordStats.get(term.getWord());
            if (stats == null) {
                stats = new Stats(posWeight * weight / lengthNorm, idf.getIDF(term));
                wordStats.put(term.getSynonyms() == null ? term.getWord() : term.getSynonyms()[0], stats);
            } else {
                stats.add(posWeight * weight / lengthNorm);
            }
        }
    }

    private TreeSet<Keyword> getResult(int topN, Map<String, Stats> wordStats) {
        TopNTreeSet<Keyword> keywords = new TopNTreeSet<Keyword>(topN, new WeightComparator());
        for (Map.Entry<String, Stats> entry : wordStats.entrySet()) {
            Stats stats = entry.getValue();
            if (stats.freq < minFreq) {
                continue;
            }
            keywords.add(new Keyword(entry.getKey(), stats.freq, Math.sqrt(stats.weight) * stats.idf));
        }

        return keywords;
    }

    private double getWeight(POS pos) {
        POS temp = pos;
        Double weight = null;
        do {
            weight = posWeights.get(temp);
            if (weight != null) {
                return weight.doubleValue();
            }
        } while ((temp = temp.getParent()) != null);
        return 1;
    }

    private KeywordExtraction(IWordSegmenter segmenter) {
        this.segmenter = segmenter;

    }

    public static class Builder {

        private KeywordExtraction extraction;

        private Set<POS> filteredPOSes;

        private Set<String> stopwords;

        public Builder(IWordSegmenter segmenter) {
            this.extraction = new KeywordExtraction(segmenter);
            this.filteredPOSes = newHashSet(POS.ag, POS.c, POS.dg, POS.e, POS.g, POS.h, POS.k, POS.m, POS.ng, POS.o,
                    POS.p, POS.q, POS.rg, POS.tg, POS.u, POS.vg, POS.vshi, POS.vu, POS.vyou, POS.y);
            this.stopwords = newHashSet();
        }

        public Builder addFilteredPOS(POS pos) {
            filteredPOSes.add(pos);
            return this;
        }

        public Builder addFilteredPOSes(POS... poses) {
            Collections.addAll(filteredPOSes, poses);
            return this;
        }

        public Builder addFilteredPOSes(List<String> poses) {
            for (String pos : poses) {
                filteredPOSes.add(POS.valueOf(pos));
            }
            return this;
        }

        public Builder addStopwords(List<String> stopwords) {
            this.stopwords.addAll(stopwords);
            return this;
        }

        public Builder addStopwords(String... stopwords) {
            Collections.addAll(this.stopwords, stopwords);
            return this;
        }

        public Builder addPOS(POS pos, double weight) {
            this.extraction.addPOS(pos, weight);
            return this;
        }

        public Builder setMinWordLength(int minWordLength) {
            this.extraction.minWordLength = minWordLength;
            return this;
        }

        public Builder setIDF(IDF idf) {
            this.extraction.setIdf(idf);
            return this;
        }

        public Builder setMinFreq(int minFreq) {
            this.extraction.minFreq = minFreq;
            return this;
        }

        public KeywordExtraction build() {
            this.extraction.stopwordDictionary = new StopwordDictionary(stopwords, filteredPOSes);
            return this.extraction;
        }
    }

    /**
     * 统计信息
     */
    private static class Stats {
        private int freq;

        private double weight;

        private double idf;

        public Stats(double weight, double idf) {
            this.freq = 1;
            this.weight = weight;
            this.idf = idf;
        }

        public void add(double weight) {
            this.freq++;
            this.weight += weight;
        }
    }
}
