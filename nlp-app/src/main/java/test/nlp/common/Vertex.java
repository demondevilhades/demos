package test.nlp.common;

import test.nlp.dict.pos.POS;
import test.nlp.util.MathUtil;

/**
 * 顶点
 */
public class Vertex implements Cloneable {

    /**
     * 该顶点对应的文本串
     */
    private String text;

    /**
     * 该顶点对应的真实词
     */
    private String word;

    private double score;

    /**
     * 该顶点所在的行索引
     */
    private int index;

    /**
     * 词元对应的的词性属性
     */
    private Attribute<POS> posAttr;

    /**
     * 标注后的词性
     */
    private POS pos;

    private Vertex from;

    public Vertex(int index, String word, Attribute<POS> posAttr) {
        this(index, word, word, posAttr);

    }

    public Vertex(int index, String word, String text, Attribute<POS> posAttr) {
        this.index = index;
        this.word = word;
        this.text = text;
        this.posAttr = posAttr;
        this.pos = posAttr.getPossibleAttr();
    }

    public POS getPos() {
        return pos;
    }

    public void setPos(POS pos) {
        this.pos = pos;
    }

    public int getIndex() {
        return index;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTotalFreq() {
        return posAttr.getTotalFreq();
    }

    public Attribute<POS> getPosAttr() {
        return posAttr;
    }

    public void setPosAttr(Attribute<POS> posAttr) {
        this.posAttr = posAttr;
    }

    public double getScore() {
        return score;
    }

    public int length() {
        return word.length();
    }

    public Vertex getFrom() {
        return from;
    }

    public void setFrom(Vertex from) {
        this.from = from;
    }

    public void updateFrom(Vertex from) {
        double score = from.score + MathUtil.computeVertexScore(from, this);
        if (this.from == null || score < this.score) {
            this.score = score;
            this.from = from;
        }
    }

    @Override
    public Object clone() {
        Vertex vertex = null;
        try {
            vertex = (Vertex) super.clone();
            vertex.score = 0;
            vertex.from = null;
        } catch (CloneNotSupportedException e) {
            // ignore
        }
        return vertex;
    }
}
