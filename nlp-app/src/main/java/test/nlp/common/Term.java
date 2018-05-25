package test.nlp.common;

import java.util.Objects;

import test.nlp.dict.pos.POS;
import test.nlp.ltp.DPTag;

import com.google.common.collect.ComparisonChain;

/**
 * 词元信息
 */
public class Term implements Comparable<Term> {

    /**
     * 词元在文本中的偏移量
     */
    protected int offset;

    /**
     * 词元
     */
    protected String word;

    /**
     * 该词的词性
     */
    protected POS pos;

    /**
     * 该词在词库中的词频
     */
    protected int freq;

    /**
     * 同义词
     */
    protected String[] synonyms;

    /**
     * 句法关系
     */
    protected DPTag tag;

    /**
     * 句法树中对应的父节点
     */
    protected Term parentTerm;

    public Term(int offset, String word) {
        this.offset = offset;
        this.word = word;
    }

    public Term(int offset, String word, POS pos, int freq) {
        this(offset, word);
        this.pos = pos;
        this.freq = freq;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public int getOffset() {
        return offset;
    }

    public int getEndOffset() {
        return offset + word.length();
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public POS getPos() {
        return pos;
    }

    public String[] getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(String[] synonyms) {
        this.synonyms = synonyms;
    }

    public void setPos(POS pos) {
        this.pos = pos;
    }

    public DPTag getTag() {
        return tag;
    }

    public void setTag(DPTag tag) {
        this.tag = tag;
    }

    public Term getParentTerm() {
        return parentTerm;
    }

    public void setParentTerm(Term parentTerm) {
        this.parentTerm = parentTerm;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != Term.class) {
            return false;
        }
        Term other = (Term) obj;
        return this.offset == other.offset && Objects.equals(this.word, other.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offset, word);
    }

    @Override
    public int compareTo(Term o) {
        return ComparisonChain.start().compare(this.offset, o.offset).compare(this.word, o.word).result();
    }

    @Override
    public String toString() {
        return pos == null ? word : word + "/" + pos.toString();
    }
}
