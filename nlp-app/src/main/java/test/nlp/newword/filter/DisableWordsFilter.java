package test.nlp.newword.filter;

import static com.google.common.collect.Sets.newHashSet;

import java.util.Set;

import test.nlp.dict.pos.POS;
import test.nlp.newword.CandidateWord;

/**
 * 禁用词过滤
 */
public class DisableWordsFilter implements CandidateWordFilter {

    public static final Set<POS> DISABLE_POSES = newHashSet(POS.al, POS.bl, POS.c, POS.dl, POS.e, POS.f, POS.i, POS.l,
            POS.m, POS.nl, POS.nr, POS.nx, POS.o, POS.p, POS.r, POS.u, POS.vl, POS.vshi, POS.vu, POS.vyou, POS.w,
            POS.x, POS.y);

    /**
     * 禁用词词性
     */
    private Set<POS> disablePOSes;

    public DisableWordsFilter() {
        this(DISABLE_POSES);
    }

    public DisableWordsFilter(Set<POS> disablePOSes) {
        this.disablePOSes = disablePOSes;
    }

    @Override
    public boolean isAccept(CandidateWord candidateWord) {
        POS[] posCombine = candidateWord.getPOSes();
        for (int i = 0; i < posCombine.length; i++) {
            if (posCombine[i].isBelong(disablePOSes)) {
                return false;
            }
        }
        return true;
    }
}
