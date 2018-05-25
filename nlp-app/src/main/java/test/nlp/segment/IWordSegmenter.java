package test.nlp.segment;

import java.util.LinkedList;
import java.util.List;

import test.nlp.common.Sentence;
import test.nlp.common.Term;
import test.nlp.dict.stopword.StopwordDictionary;

/**
 * 分词接口
 */
public interface IWordSegmenter {

    /**
     * 分词
     * 
     * @param text
     *            待切词的文本
     * @return
     */
    LinkedList<Term> segment(String text);

    /**
     * 分词
     * 
     * @param text
     *            待切词的文本
     * @param stopwordDictionary
     *            停用词典
     * @return
     */
    LinkedList<Term> segment(String text, StopwordDictionary stopwordDictionary);

    /**
     * 分词
     * 
     * @param charArray
     *            待分词的文本转换成的字符数组
     * @return
     */
    LinkedList<Term> segment(char[] charArray);

    /**
     * 分词
     * 
     * @param charArray
     *            待分词的文本转换成的字符数组
     * @param stopwordDictionary
     *            停用词典
     * @return
     */
    LinkedList<Term> segment(char[] charArray, StopwordDictionary stopwordDictionary);

    LinkedList<Term> segment(Sentence sentence);

    LinkedList<Term> segment(Sentence sentence, StopwordDictionary stopwordDictionary);

    /**
     * 分词
     * 
     * @param sentences
     *            句子信息
     * @return
     */
    LinkedList<Term> segment(List<Sentence> sentences);

    /**
     * 分词
     * 
     * @param sentences
     *            句子信息
     * @param stopwordDictionary
     *            停用词典
     * @return
     */
    LinkedList<Term> segment(List<Sentence> sentences, StopwordDictionary stopwordDictionary);

    /**
     * 是否开启人名识别
     * 
     * @param enableNameRecognize
     * @return
     */
    IWordSegmenter enableNameRecognize(boolean enableNameRecognize);

    /**
     * 是否开启地名识别
     * 
     * @param enablePlaceRecognize
     * @return
     */
    IWordSegmenter enablePlaceRecognize(boolean enablePlaceRecognize);

    /**
     * 是否开启数量词识别功能
     * 
     * @param enableNumberRecognize
     * @return
     */
    IWordSegmenter enableNumberRecognize(boolean enableNumberRecognize);

    /**
     * 是否开启词性标注功能
     * 
     * @param enablePOSTagger
     * @return
     */
    IWordSegmenter enablePOSTagger(boolean enablePOSTagger);

}
