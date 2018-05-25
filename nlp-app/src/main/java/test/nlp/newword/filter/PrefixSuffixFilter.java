package test.nlp.newword.filter;

import static com.google.common.collect.Sets.newHashSet;

import java.util.Set;

import test.nlp.dict.pos.POS;
import test.nlp.newword.CandidateWord;

/**
 * 前后缀过滤
 */
public class PrefixSuffixFilter implements CandidateWordFilter {

    private Set<POS> prefixes;

    private Set<POS> suffixes;

    public PrefixSuffixFilter() {
        this.prefixes = newHashSet(POS.a, POS.d, POS.h);
        this.suffixes = newHashSet(POS.k, POS.q);
    }

    @Override
    public boolean isAccept(CandidateWord candidateWord) {
        POS[] poses = candidateWord.getPOSes();
        if (poses[0].isBelong(suffixes) || poses[poses.length - 1].isBelong(prefixes)) {
            return false;
        }
        return true;
    }
}
