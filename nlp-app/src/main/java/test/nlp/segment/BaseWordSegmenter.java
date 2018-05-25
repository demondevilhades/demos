package test.nlp.segment;

import static com.google.common.collect.Lists.newLinkedList;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import test.nlp.Constant;
import test.nlp.common.Atom;
import test.nlp.common.Attribute;
import test.nlp.common.LocationTerm;
import test.nlp.common.Sentence;
import test.nlp.common.Term;
import test.nlp.common.Vertex;
import test.nlp.dict.CoreDictionary;
import test.nlp.dict.custom.CustomDictionary;
import test.nlp.dict.pos.POS;
import test.nlp.dict.pos.POSAttribute;
import test.nlp.dict.stopword.StopwordDictionary;
import test.nlp.util.CharacterUtil.CharType;
import test.nlp.util.SentenceUtil;

/**
 * 分词基类，提供一些公共方法
 */
public abstract class BaseWordSegmenter implements IWordSegmenter {

    /**
     * 领域
     */
    protected String domain;

    protected Config config = new Config();

    protected CoreDictionary coreDictionary;

    protected CustomDictionary customDictionary;

    public BaseWordSegmenter(String domain) {
        this.domain = domain;
        this.coreDictionary = CoreDictionary.getInstance();
        this.customDictionary = CustomDictionary.getInstance();
    }

    @Override
    public LinkedList<Term> segment(char[] charArray) {
        return segment(charArray, StopwordDictionary.NULL);
    }

    @Override
    public LinkedList<Term> segment(char[] charArray, StopwordDictionary stopwordDictionary) {
        return segment(SentenceUtil.split(charArray), stopwordDictionary);
    }

    @Override
    public LinkedList<Term> segment(String text) {
        return segment(text.toCharArray(), StopwordDictionary.NULL);
    }

    @Override
    public LinkedList<Term> segment(String text, StopwordDictionary stopwordDictionary) {
        return segment(text.toCharArray(), stopwordDictionary);
    }

    @Override
    public LinkedList<Term> segment(Sentence sentence, StopwordDictionary stopwordDictionary) {
        LinkedList<Term> terms = segment(sentence);
        stopwordDictionary.filter(terms);
        return terms;
    }

    @Override
    public LinkedList<Term> segment(List<Sentence> sentences) {
        return segment(sentences, StopwordDictionary.NULL);
    }

    @Override
    public LinkedList<Term> segment(List<Sentence> sentences, StopwordDictionary stopwordDictionary) {
        LinkedList<Term> terms = newLinkedList();
        for (Sentence sentence : sentences) {
            terms.addAll(segment(sentence, stopwordDictionary));
        }
        return terms;
    }

    @Override
    public IWordSegmenter enableNameRecognize(boolean nameRecognize) {
        config.enableNameRecognize(nameRecognize);
        return this;
    }

    @Override
    public IWordSegmenter enablePlaceRecognize(boolean placeRecognize) {
        config.enablePlaceRecognize(placeRecognize);
        return this;
    }

    @Override
    public IWordSegmenter enableNumberRecognize(boolean enableNumberRecognize) {
        this.config.enableNumberRecognize(enableNumberRecognize);
        return this;
    }

    @Override
    public IWordSegmenter enablePOSTagger(boolean enablePOSTagger) {
        this.config.enablePOSTagger(enablePOSTagger);
        return this;
    }

    /**
     * 生成分词图表
     * 
     * @param sentence
     * @return
     */
    protected Graph generateGraph(Sentence sentence) {
        Graph graph = new Graph(sentence.length());
        coreDictionary.segment(sentence, graph);
        // 单字切分
        for (int i = 0, size = sentence.size(); i < size; i++) {
            Atom atom = sentence.getAtom(i);
            LinkedList<Vertex> vertexes = graph.getVertexes(atom.getOffset());
            if (vertexes.isEmpty() || vertexes.getLast().length() > atom.length()) {
                Pair<String, Attribute<POS>> unknownPair = getUnknownPair(atom.getWord(), atom.getCharType());
                vertexes.addLast(new Vertex(atom.getOffset(), atom.getWord(), unknownPair.getLeft(), unknownPair
                        .getRight()));

            }
        }
        return graph;
    }

    /**
     * 根据字符类型获取未知词信息
     * 
     * @param charType
     * @return
     */
    protected Pair<String, Attribute<POS>> getUnknownPair(String word, CharType charType) {

        Pair<String, Attribute<POS>> unkonwnPair = null;
        switch (charType.getRootType()) {
        case CT_LETTER:
            unkonwnPair = Pair.of(Constant.UNKNOWN_LETTER,
                    CoreDictionary.getInstance().getUnknownPOSAttr(Constant.UNKNOWN_LETTER, new POS[] { POS.nx }));
            break;
        case CT_NUMBER:
            unkonwnPair = Pair.of(Constant.UNKNOWN_NUM,
                    CoreDictionary.getInstance().getUnknownPOSAttr(Constant.UNKNOWN_NUM, new POS[] { POS.m }));
            break;
        case CT_PUNC:
            unkonwnPair = Pair.of(Constant.UNKNOWN_PUNC,
                    CoreDictionary.getInstance().getUnknownPOSAttr(Constant.UNKNOWN_LETTER, new POS[] { POS.w }));
            break;
        case CT_WHITESPACE:
            unkonwnPair = Pair.of(word, (Attribute<POS>) new POSAttribute(0));
            break;
        case CT_OTHER:
            unkonwnPair = Pair.of(word, (Attribute<POS>) new POSAttribute(POS.x, 1));
            break;
        default:
            unkonwnPair = Pair.of(word, (Attribute<POS>) new POSAttribute(POS.g, 1));
            break;
        }
        return unkonwnPair;
    }

    protected Term createTerm(int offset, Vertex vertex) {

        if (vertex.getPos() != null && vertex.getPos().isBelong(POS.ns)) {
            return new LocationTerm(offset + vertex.getIndex(), vertex.getWord(), vertex.getPos(), vertex.getPosAttr()
                    .getTotalFreq());
        }
        return new Term(offset + vertex.getIndex(), vertex.getWord(), vertex.getPos(), vertex.getPosAttr()
                .getTotalFreq());

    }

}
