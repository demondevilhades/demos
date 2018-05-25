package test.nlp.newword;

import java.util.Comparator;

import test.nlp.common.Term;

/**
 * 前缀PAT Array
 */
public class PrefixPATArray extends PATArray {

    public PrefixPATArray(String text) {
        super(text);
    }

    public PrefixPATArray(Term[] terms) {
        super(terms);
    }

    @Override
    protected int getCommonLength(int offset1, int offset2) {
        int length = 0;
        for (int i = offset1, j = offset2; i >= 0 && j >= 0; i--, j--) {
            if (terms[i].getWord().equals(terms[j].getWord())) {
                length++;
            } else {
                break;
            }
        }
        return length;
    }

    @Override
    protected int getAjacentTermIndex(int position, int length) {
        return array[position] - length;
    }

    @Override
    protected int getStartIndex(int position, int length) {
        return getAjacentTermIndex(position, length) + 1;
    }

    @Override
    protected Comparator<Integer> newComparator() {
        return new Comparator<Integer>() {

            @Override
            public int compare(Integer index1, Integer index2) {
                while (index1 >= 0 && index2 >= 0 && terms[index1].getWord().equals(terms[index2].getWord())) {
                    index1--;
                    index2--;
                }
                if (index1 >= 0 && index2 >= 0) {
                    return terms[index1].getWord().compareTo(terms[index2].getWord());
                }
                if (index1 >= 0) {
                    return 1;
                }
                return -1;
            }
        };
    }
}
