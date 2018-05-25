package test.nlp.keyword;

import test.nlp.common.Term;
import test.nlp.dict.CoreDictionary;

/**
 * IDF默认实现
 */
public class DefaultIDF implements IDF {

    public static final DefaultIDF INSTANCE = new DefaultIDF();

    private static final double MIN_IDF = Math.log(CoreDictionary.TOTAL_FREQ / (double) (CoreDictionary.TOTAL_FREQ + 1)
            + 1.0);

    @Override
    public double getIDF(Term term) {

        if (term.getSynonyms() == null) {
            return getIDF(term.getWord());
        }

        double idf = Double.MAX_VALUE;
        for (int i = 0; i < term.getSynonyms().length; i++) {
            idf = Math.min(idf, getIDF(term.getSynonyms()[i]));
        }
        return idf;
    }

    private double getIDF(String word) {
        int freq = CoreDictionary.getInstance().getFreq(word);
        if (freq >= CoreDictionary.TOTAL_FREQ) {
            return MIN_IDF;
        }
        return Math.log(CoreDictionary.TOTAL_FREQ / (double) (freq + 1) + 1.0);
    }
}
