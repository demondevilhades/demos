package test.nlp.util;

import java.io.Serializable;
import java.util.LinkedList;

import test.nlp.common.Term;
import test.nlp.common.Vertex;
import test.nlp.dict.pos.POS;
import test.nlp.trie.IHit;
import test.nlp.trie.ITrie;
import test.nlp.trie.Node;

import com.google.common.base.Joiner;

/**
 * 分词工具类
 */
public class SegmentUtil {

    private static final Joiner SEGMENT_JOINER = Joiner.on('\t').skipNulls();

    public static String toString(LinkedList<Term> terms) {
        if (terms.isEmpty()) {
            return "";
        }

        return SEGMENT_JOINER.join(terms);
    }

    public static <V extends Serializable> void fmm(ITrie<V> dict, Vertex[] vertexes, IHit<V> hit) {
        Node<V> node = null;
        int matchIndex = -1;
        V value = null;
        for (int i = 0; i < vertexes.length; i++) {
            if (vertexes[i] == null) {
                continue;
            }
            node = dict.getRoot();
            matchIndex = -1;
            for (int j = i; j < vertexes.length; j++) {
                if (vertexes[j] == null) {
                    break;
                }
                node = dict.transition(vertexes[j].getWord(), node);
                if (node == null) {
                    break;
                }
                if (node.isWord()) {
                    matchIndex = j;
                    value = node.getValue();
                }
            }

            if (matchIndex != -1) {
                hit.hit(i, matchIndex, value);
                i = matchIndex;
            } else {
                hit.notHit(i);
            }
        }
    }

    public static <V extends Serializable> void fmmOnce(int startIndex, ITrie<V> dict, Term[] terms, IHit<V> hit) {

        int matchIndex = -1;
        V value = null;
        Node<V> node = dict.getRoot();
        for (int i = startIndex; i < terms.length; i++) {
            node = dict.transition(terms[i].getWord(), node);
            if (node == null) {
                break;
            }
            if (node.isWord()) {
                matchIndex = i;
                value = node.getValue();
                if (node.isLeaf()) {
                    break;
                }
            }
        }

        if (matchIndex != -1) {
            hit.hit(startIndex, matchIndex, value);
        } else {
            hit.notHit(startIndex);
        }
    }

    /**
     * 对已切分好的词语再次最大化切词
     * 
     * @param dict
     * @param terms
     * @param hit
     */
    public static <V extends Serializable> void fmm(ITrie<V> dict, Term[] terms, IHit<V> hit) {
        fmm(0, terms.length, dict, terms, hit);
    }

    /**
     * 对已切分好的词语再次最大化切词
     * 
     * @param dict
     * @param terms
     * @param hit
     */
    public static <V extends Serializable> void fmm(int startIndex, int endIndex, ITrie<V> dict, Term[] terms,
            IHit<V> hit) {
        Node<V> node = null;
        int matchIndex = -1;
        V value = null;
        for (int i = startIndex; i < endIndex; i++) {
            if (terms[i] == null) {
                continue;
            }
            node = dict.getRoot();
            matchIndex = -1;
            for (int j = i; j < endIndex; j++) {
                if (terms[j] == null) {
                    continue;
                }
                node = dict.transition(terms[j].getWord(), node);
                if (node == null) {
                    break;
                }
                if (node.isWord()) {
                    matchIndex = j;
                    value = node.getValue();
                }
            }

            if (matchIndex != -1) {
                hit.hit(i, matchIndex, value);
                i = matchIndex;
            } else {
                hit.notHit(i);
            }
        }
    }

    public static LinkedList<Term> trim(LinkedList<Term> terms) {
        while (!terms.isEmpty()) {
            Term term = terms.getFirst();
            if (term.getPos() == null || term.getPos().isBelong(POS.w)) {
                terms.removeFirst();
                continue;
            }
            break;
        }

        while (!terms.isEmpty()) {
            Term term = terms.getLast();
            if (term.getPos() == null || term.getPos().isBelong(POS.w)) {
                terms.removeLast();
                continue;
            }
            break;
        }
        return terms;
    }

}
