package test.nlp.ltp;

import static com.google.common.collect.Lists.newLinkedList;

import java.util.Collection;
import java.util.LinkedList;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import test.nlp.common.Term;

/**
 * 句法树
 */
public class SyntacticTree {

    /**
     * 核心词
     */
    private Term headTerm;

    /**
     * 从根结点起始第一个有意义的谓语动词
     */
    private Term firstPredicateTerm;

    private Multimap<Term, Term> tree;

    public SyntacticTree(LinkedList<Term> terms) {
        create(terms);
    }

    public Term getHeadTerm() {
        return headTerm;
    }

    public Term getFirstPredicateTerm() {
        return firstPredicateTerm;
    }

    /**
     * 返回满足句法关系的子节点
     * 
     * @param curTerm
     * @param tags
     * @return
     */
    public LinkedList<Term> getSubTerms(Term curTerm, DPTag... tags) {
        Collection<Term> subTerms = tree.get(curTerm);
        if (subTerms == null) {
            return newLinkedList();
        }
        if (ArrayUtils.isEmpty(tags)) {
            return newLinkedList(subTerms);
        }

        LinkedList<Term> result = newLinkedList();
        if (subTerms != null) {
            for (Term term : subTerms) {
                if (ArrayUtils.contains(tags, term.getTag())) {
                    result.add(term);
                }
            }
        }
        return result;
    }

    private void create(LinkedList<Term> terms) {

        tree = ArrayListMultimap.create();
        for (Term term : terms) {
            if (term.getParentTerm() != null) {
                tree.put(term.getParentTerm(), term);
            } else {
                headTerm = term;
            }

        }

    }

}
