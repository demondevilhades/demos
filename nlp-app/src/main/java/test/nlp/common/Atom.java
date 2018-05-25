package test.nlp.common;

import test.nlp.util.CharacterUtil.CharType;

/**
 * 原子对象
 */
public class Atom {

    /**
     * 原子词
     */
    private String word;

    /**
     * 字符类型
     */
    private CharType charType;

    /**
     * 在句子中的位置
     */
    private int offset;

    public Atom(int offset, CharType charType) {
        this.offset = offset;
        this.charType = charType;
    }

    public Atom(int offset, CharType charType, String word) {
        this(offset, charType);
        this.word = word;
    }

    public CharType getCharType() {
        return charType;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getOffset() {
        return offset;
    }

    public int length() {
        return word.length();
    }

    public String toString() {
        return word + "/" + charType;
    }

}
