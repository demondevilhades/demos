package test.nlp.keyword;

import test.nlp.common.Term;

/**
 * IDF信息接口
 */
public interface IDF {

    /**
     * 获取某个词的IDF值
     * 
     * @param word
     * @return
     */
    double getIDF(Term term);
}
