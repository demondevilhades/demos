package test.nlp.ltp;

import static com.google.common.collect.Lists.newArrayListWithCapacity;
import static com.google.common.collect.Lists.newArrayListWithExpectedSize;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import test.nlp.common.Term;

/**
 * ltp解析基类
 */
public abstract class LTPBaseParser implements LTPParser {

    @Override
    public LinkedList<Term> parse(LinkedList<Term> terms) {
        Term[] termArray = new Term[terms.size()];
        Iterator<Term> iterator = terms.iterator();
        List<String> words = newArrayListWithExpectedSize(termArray.length);
        List<String> posTags = newArrayListWithExpectedSize(termArray.length);
        int index = 0;
        while (iterator.hasNext()) {
            Term term = iterator.next();
            if (term.getPos() == null) {
                iterator.remove();
                continue;
            }
            termArray[index++] = term;
            words.add(term.getWord());
            posTags.add(term.getPos().getLTPPOS().toString());
        }

        List<Integer> heads = newArrayListWithCapacity(words.size());
        List<String> deprels = newArrayListWithCapacity(posTags.size());

        parse(words, posTags, heads, deprels);

        for (int i = 0, size = words.size(); i < size; i++) {
            termArray[i].setTag(DPTag.valueOf(deprels.get(i)));
            int head = heads.get(i) - 1;
            if (head >= 0) {
                termArray[i].setParentTerm(termArray[head]);
            }
        }
        return terms;
    }

}
