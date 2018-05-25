package test.nlp.segment;

import java.util.LinkedList;

import com.google.common.collect.Lists;
import test.nlp.common.Atom;
import test.nlp.common.Sentence;
import test.nlp.common.Term;

/**
 * 单字切分
 */
public class SingleWordSegmenter extends BaseWordSegmenter {

    public SingleWordSegmenter(String domain) {
        super(domain);
    }

    @Override
    public LinkedList<Term> segment(Sentence sentence) {
        LinkedList<Term> terms = Lists.newLinkedList();
        for (int i = 0, size = sentence.size(); i < size; i++) {
            Atom atom = sentence.getAtom(i);
            Term term = new Term(sentence.getOffset() + atom.getOffset(), atom.getWord());
            terms.add(term);
        }
        return terms;
    }

}
