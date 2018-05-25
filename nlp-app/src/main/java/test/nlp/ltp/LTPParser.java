package test.nlp.ltp;

import java.util.LinkedList;
import java.util.List;

import test.nlp.common.Term;
import test.nlp.dict.pos.POS;

/**
 * LTP句法分析类
 */
public interface LTPParser {

    /**
     * 利用ltp获取词的词性
     * 
     * @param word
     * @return
     */
    POS getPOS(String word);

    /**
     * 句法分析
     * 
     * @param words
     *            待分析的词序列
     * @param tags
     *            待分析的词的词性序列
     * @param heads
     *            结果依存弧，heads[i]代表第i个词的父亲节点的编号
     * @param deprels
     *            结果依存弧关系类型
     */
    void parse(List<String> words, List<String> tags, List<Integer> heads, List<String> deprels);

    /**
     * 对文本进行分词、词性标注及句法分析。
     * 
     * @param text
     * @return
     */
    List<LinkedList<Term>> parse(String text);

    /**
     * 对分词结果进行句法分析
     * 
     * @param terms
     * @return
     */
    LinkedList<Term> parse(LinkedList<Term> terms);

}
