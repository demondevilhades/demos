package test.nlp.util;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import test.nlp.common.Atom;
import test.nlp.common.Sentence;
import test.nlp.util.CharacterUtil.CharType;

/**
 * 句子操作工具类
 */
public class SentenceUtil {

    /**
     * 将文本切分成句子
     * 
     * @param text
     * @return
     */
    public static List<Sentence> split(String text) {
        return split(text, Collections.<Character> emptySet());
    }

    /**
     * 将文本切分成句子
     * 
     * @param text
     *            待切分的文本
     * @param sentenceSplitChars
     *            用于切分句子的字符
     * @return
     */
    public static List<Sentence> split(String text, Set<Character> sentenceSplitChars) {
        if (StringUtils.isBlank(text)) {
            return Collections.emptyList();
        }
        return split(text.toCharArray(), Collections.<Character> emptySet());
    }

    /**
     * 将文本切分成句子
     * 
     * @param charArray
     *            待切分文本对应的字符数组
     * @return
     */
    public static List<Sentence> split(char[] charArray) {
        return split(charArray, Collections.<Character> emptySet());

    }

    /**
     * 将文本切分成句子
     * 
     * @param charArray
     *            待切分文本对应的字符数组
     * @param sentenceSplitChars
     *            用于切分句子的字符
     * @return
     */
    public static List<Sentence> split(char[] charArray, Set<Character> sentenceSplitChars) {
        test.nlp.util.StringUtils.regularize(charArray, 0, charArray.length);
        LinkedList<Sentence> sentences = Lists.newLinkedList();
        Sentence sentence = new Sentence(0);
        int offset = 0;
        for (int i = 0; i < charArray.length;) {
            char ch = charArray[i];
            CharType charType = CharacterUtil.getCharType(ch);
            if (CharType.CT_PARAGRAPH_SEG == charType) {
                i++;
                if (!sentence.isEmpty()) {
                    sentences.add(sentence);
                    sentence = new Sentence(i);
                }
                offset = 0;
            } else if (CharType.CT_SENTENCE_SEG == charType || sentenceSplitChars.contains(ch)) {
                i++;
                if (!sentence.isEmpty()) {
                    sentence.addAtom(new Atom(offset, charType, ch + ""));
                    sentences.add(sentence);
                    sentence = new Sentence(i);
                }
                offset = 0;
            } else {
                Atom atom = null;
                switch (charType.getRootType()) {
                case CT_CJK:
                    atom = new Atom(offset, charType, ch + "");
                    break;
                case CT_LETTER:
                case CT_NUMBER:
                    atom = createAtom(offset, i, charArray, charType);
                    break;
                case CT_PUNC:
                    atom = new Atom(offset, charType, ch + "");
                    break;
                case CT_WHITESPACE:
                    atom = new Atom(offset, charType, " ");
                    break;
                default:
                    atom = new Atom(offset, charType, ch + "");
                }
                sentence.addAtom(atom);
                offset += atom.length();
                i += atom.length();
            }
        }

        // 处理边界
        if (sentence.size() > 0) {
            sentences.add(sentence);
        }

        return sentences;
    }

    private static Atom createAtom(int offset, int index, char[] charArray, CharType charType) {
        int endIndex = index;
        CharType curCharType = charType;
        CharType nextCharType = null;
        for (int i = index + 1; i < charArray.length; i++) {
            nextCharType = CharacterUtil.getCharType(charArray[i]);

            if (CharType.CT_NUMBER == nextCharType || CharType.CT_LETTER == nextCharType) {
                endIndex = i;
                if (CharType.CT_NUMBER == curCharType || CharType.CT_LETTER == nextCharType) {
                    curCharType = CharType.CT_LETTER;
                }
            } else if (CharType.CT_CONNECTOR != nextCharType && '.' != charArray[i]) {
                break;
            }

        }

        return new Atom(offset, curCharType, new String(charArray, index, endIndex - index + 1));

    }

}
