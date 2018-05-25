package test.nlp.newword;

import com.google.common.base.Joiner;
import test.nlp.dict.pos.POS;
import test.nlp.ltp.LTPParser;
import test.nlp.trie.ITrie;
import test.nlp.trie.Trie;

/**
 * 候选词词性识别类
 */
public class POSRecognition {

    private static final String POS_CONNECTOR = "+";

    private static final Joiner POS_JOINER = Joiner.on(POS_CONNECTOR).skipNulls();

    /**
     * 词性识别规则
     */
    private static ITrie<POS> rules;

    static {
        rules = new Trie<>();

        rules.add(POS.a + POS_CONNECTOR + POS.n, POS.n);
        rules.add(POS.b + POS_CONNECTOR + POS.n, POS.n);
        rules.add(POS.h + POS_CONNECTOR + POS.n, POS.n);
        rules.add(POS.j + POS_CONNECTOR + POS.n, POS.n);
        rules.add(POS.n + POS_CONNECTOR + POS.n, POS.n);
        rules.add(POS.n + POS_CONNECTOR + POS.g, POS.n);
        rules.add(POS.n + POS_CONNECTOR + POS.k, POS.n);

        rules.add(POS.d + POS_CONNECTOR + POS.v, POS.v);
        rules.add(POS.d + POS_CONNECTOR + POS.vi, POS.vi);
        rules.add(POS.v + POS_CONNECTOR + POS.v, POS.v);

    }

    private LTPParser ltpParser;

    public POSRecognition(LTPParser ltpParser) {
        this.ltpParser = ltpParser;
    }

    public POS recognize(CandidateWord candidateWord) {
        POS pos = null;
        if (ltpParser != null) {
            pos = ltpParser.getPOS(candidateWord.getWord());
        }

        if (pos == null || pos.isBelong(POS.nr)) {
            pos = rules.get(POS_JOINER.join(candidateWord.getPOSes()));
            if (pos == null) {
                StringBuilder key = new StringBuilder();
                POS[] poses = candidateWord.getPOSes();
                for (int i = 0; i < poses.length; i++) {
                    key.append(getParent(poses[i]));
                    if (i < poses.length - 1) {
                        key.append(POS_CONNECTOR);
                    }
                }
                pos = rules.get(key.toString());
            }
        }
        return pos;

    }

    private static POS getParent(POS pos) {
        POS curPOS = pos;
        POS parent = pos;
        while (curPOS.getParent() != null) {
            parent = curPOS.getParent();
            curPOS = parent;
        }
        return parent;
    }

}
