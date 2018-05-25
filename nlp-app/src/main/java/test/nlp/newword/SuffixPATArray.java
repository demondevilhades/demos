package test.nlp.newword;

import java.util.Comparator;

import test.nlp.common.Term;

/**
 * 后缀PAT Array
 */
public class SuffixPATArray extends PATArray {

    public SuffixPATArray(String text) {
        super(text);
    }

    public SuffixPATArray(Term[] terms) {
        super(terms);
    }

    protected int getAjacentTermIndex(int position, int length) {
        return array[position] + length;
    }

    @Override
    protected int getCommonLength(int offset1, int offset2) {
        int length = 0;

        for (int i = offset1, j = offset2; i < terms.length && j < terms.length; i++, j++) {
            if (terms[i].getWord().equals(terms[j].getWord())) {
                length++;
            } else {
                break;
            }
        }
        return length;
    }

    @Override
    protected int getStartIndex(int position, int length) {
        return array[position];
    }

    @Override
    protected Comparator<Integer> newComparator() {
        return new Comparator<Integer>() {
            @Override
            public int compare(Integer index1, Integer index2) {

                while (index1 < length && index2 < length && terms[index1].getWord().equals(terms[index2].getWord())) {
                    index1++;
                    index2++;
                }
                if (index1 < length && index2 < length) {
                    return terms[index1].getWord().compareTo(terms[index2].getWord());
                } else if (index1 < length) {
                    return 1;
                }
                return -1;
            }
        };
    }

}
