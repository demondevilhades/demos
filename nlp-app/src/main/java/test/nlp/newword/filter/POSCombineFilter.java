package test.nlp.newword.filter;

import static com.google.common.collect.Sets.newHashSet;

import java.util.Set;

import com.google.common.base.Joiner;
import test.nlp.dict.pos.POS;
import test.nlp.newword.CandidateWord;

/**
 * 词性组合规则过滤
 */
public class POSCombineFilter implements CandidateWordFilter {

    private static final String POS_CONNECTOR = "+";

    private static final Joiner JOINER = Joiner.on(POS_CONNECTOR).skipNulls();

    private Set<String> filterRules;

    public POSCombineFilter() {
        this.filterRules = newHashSet();
        this.filterRules.add(POS.nr1 + POS_CONNECTOR + POS.nnt);
    }

    @Override
    public boolean isAccept(CandidateWord candidateWord) {
        POS[] poses = candidateWord.getPOSes();
        return !this.filterRules.contains(JOINER.join(poses));
    }
}
