package test.nlp.corpus;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Objects;
import test.nlp.dict.pos.POS;

/**
 * 一个切分单元
 */
public class Token<V extends Enum<V>> {

    protected String preTag = "";

    protected String postTag = "";

    protected String word;

    /**
     * 词性
     */
    protected POS pos;

    protected V label;

    public Token() {

    }

    public Token(String text) {
        this.word = StringUtils.substringBeforeLast(text, "/");
        String temp = text.substring(word.length() + 1);
        if (this.word.startsWith("[") && this.word.length() > 1) {
            this.preTag = "[";
            this.word = this.word.substring(1);
        }
        if (!"]".equals(this.word)) {
            int index = temp.indexOf("]");
            if (index != -1) {
                this.postTag = temp.substring(index);
                temp = temp.substring(0, index);
            }
        }
        this.pos = POS.valueOf(temp);
    }

    public Token(String word, POS pos, V label) {
        this.word = word;
        this.pos = pos;
        this.label = label;
    }

    public Token(String word, V label) {
        this.word = word;
        this.label = label;
    }

    public POS getPos() {
        return pos;
    }

    public void setPos(POS pos) {
        this.pos = pos;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public V getLabel() {
        return label;
    }

    public void setLabel(V label) {
        this.label = label;
    }

    public String getPreTag() {
        return preTag;
    }

    public String getPostTag() {
        return postTag;
    }

    public int length() {
        return word.length();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Token) {
            return Objects.equal(this.word, ((Token<V>) obj).word) && Objects.equal(this.pos, ((Token<V>) obj).pos);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(word, pos);
    }

    @Override
    public String toString() {
        return preTag + word + "/" + pos.toString() + postTag;
    }
}