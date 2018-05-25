package test.nlp.highlight;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Sets;
import test.nlp.common.Atom;
import test.nlp.common.Sentence;
import test.nlp.trie.ITrie;
import test.nlp.trie.Node;
import test.nlp.trie.Trie;
import test.nlp.util.CharacterUtil.CharType;
import test.nlp.util.PorterStemmer;
import test.nlp.util.SentenceUtil;

/**
 * 高亮
 */
public class Highlighter {

    public static final String PRE_TAG = "<em>";

    public static final String POST_TAG = "</em>";

    private ITrie<Integer> dict;

    private final PorterStemmer stemmer = new PorterStemmer();

    private int tokenNum;

    private String preTag;

    private String postTag;

    public Highlighter(Set<String> tokens) {
        this(tokens, PRE_TAG, POST_TAG);
    }

    public Highlighter(Set<String> tokens, String preTag, String postTag) {
        if (tokens != null) {
            this.tokenNum = tokens.size();
            this.dict = new Trie<Integer>();
            int index = 0;
            for (String token : tokens) {
                dict.add(token, index);
                index++;
            }
        }
        this.preTag = preTag;
        this.postTag = postTag;
    }

    /**
     * 获取摘要并高亮关键词
     * 
     * @param text
     *            需要提取的文本
     * @param length
     *            提取长度
     * @return
     */
    public String highlight(String text, int length) {
        if (StringUtils.isBlank(text)) {
            return "";
        }
        if (length <= 0) {
            length = text.length();
        }

        if (dict == null) {
            return length > text.length() ? text : text.substring(0, length) + "...";
        }

        char[] charArray = text.toCharArray();
        List<Sentence> sentences = SentenceUtil.split(charArray);
        Summary summary = new Summary(preTag, postTag);
        for (Sentence sentence : sentences) {
            Summary.Sentence summarySentence = processSentence(sentence);
            summary.addSentence(summarySentence);
            // 已经找到最匹配的句子并且与其后句子的总长度大于所需要摘要的长度
            if (summary.getScore() >= tokenNum && summary.getLength() >= length) {
                break;
            }
        }

        // 没有匹配到句子，直接返回前length个字符
        if (summary.getScore() == 0) {
            return length >= text.length() ? text : text.substring(0, length) + "...";
        }
        return summary.getSummary(charArray, length);
    }

    protected Summary.Sentence processSentence(Sentence sentence) {
        Summary.Sentence summarySentence = new Summary.Sentence(sentence.getOffset(), sentence.length(), tokenNum);

        for (int i = 0, size = sentence.size(); i < size; i++) {
            maxMatch(sentence, size, i, summarySentence);
        }

        return summarySentence;
    }

    private void maxMatch(Sentence sentence, int size, int offset, Summary.Sentence summarySentence) {

        int start = sentence.getAtom(offset).getOffset();
        int end = offset;

        Node<Integer> node = dict.getRoot();
        for (int i = offset; i < size; i++) {
            Atom atom = sentence.getAtom(i);
            node = dict.transition(atom.getWord(), node);
            if (node == null) {
                break;
            }
            if (node.isWord()) {
                summarySentence.addHit(node.getValue());
                end = atom.getOffset() + atom.length();
            }
        }

        if (end > start) {
            summarySentence.setHighlightTags(start, end);
        } else {
            Atom atom = sentence.getAtom(offset);
            // 英文字符，提取词干后再匹配
            if (CharType.CT_LETTER == atom.getCharType()) {
                String stemmerWord = stemmer.stem(atom.getWord());
                Integer index = dict.get(stemmerWord);
                if (index != null) {
                    summarySentence.addHit(index.intValue());
                    summarySentence.setHighlightTags(start, start + atom.length());

                }
            }

        }
    }

    public static void main(String[] args) {
        Set<String> tokens = Sets.newHashSet(new String[] { "天坛生物", "生物" });
        Highlighter highlighter = new Highlighter(tokens);
        String content = "天坛生物";
        System.out.println(highlighter.highlight(content, 200));
    }

}
