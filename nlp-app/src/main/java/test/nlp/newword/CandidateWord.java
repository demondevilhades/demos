package test.nlp.newword;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.base.Objects;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import test.nlp.Constant;
import test.nlp.dict.pos.POS;

/**
 * 候选词信息
 */
public class CandidateWord {

    /**
     * 词元
     */
    private String word;

    /**
     * 词频
     */
    private int freq;

    /**
     * 互信息
     */
    private double mi;

    /**
     * term组合信息, key：word1/pos1 word2/pos2 ...
     */
    private Multiset<String> termCombines = HashMultiset.create();

    /**
     * 左邻词信息
     */
    private Multiset<String> leftAdjacents = HashMultiset.create();

    /**
     * 右邻词信息
     */
    private Multiset<String> rightAdjacents = HashMultiset.create();

    /**
     * 左信息熵
     */
    private double le = -1;

    /**
     * 在信息熵
     */
    private double re = -1;

    /**
     * 最可能的词组合
     */
    private String[] words;

    /**
     * 最可能的词性组合
     */
    private POS[] poses;

    /**
     * 候选词词性
     */
    private POS pos;

    public CandidateWord(String word, Multiset<String> termCombines) {
        this.word = word;
        this.termCombines = termCombines;
        this.freq = termCombines.size();
    }

    public CandidateWord(String word, int freq) {
        this.word = word;
        this.freq = freq;
    }

    public CandidateWord(String word, int freq, POS pos) {
        this.word = word;
        this.freq = freq;
        this.pos = pos;
    }

    public String getWord() {
        return word;
    }

    public int getFreq() {
        return freq;
    }

    public void addFreq(int freq) {
        this.freq += freq;
    }

    public double getMi() {
        return mi;
    }

    public void setMi(double mi) {
        this.mi = mi;
    }

    public double getLe() {
        if (le == -1) {
            le = calcEntropy(leftAdjacents);
        }
        return le;
    }

    public void setLe(double le) {
        this.le = le;
    }

    public double getRe() {
        if (re == -1) {
            re = calcEntropy(rightAdjacents);

        }
        return re;
    }

    public void setRe(double re) {
        this.re = re;
    }

    public POS getPOS() {
        return pos;
    }

    public void setPOS(POS pos) {
        this.pos = pos;
    }

    public String[] getWords() {
        if (words == null && termCombines != null) {
            calcMaxTermCombine();
        }
        return words;

    }

    private void calcMaxTermCombine() {
        if (termCombines.isEmpty()) {
            words = new String[0];
            poses = new POS[0];
            return;
        }
        String maxTermCombine = null;
        int maxFreq = 0;
        for (String termCombine : termCombines.elementSet()) {
            int freq = termCombines.count(termCombine);
            if (freq > maxFreq) {
                maxFreq = freq;
                maxTermCombine = termCombine;
            }
        }
        String[] splits = StringUtils.split(maxTermCombine, ' ');
        words = new String[splits.length];
        poses = new POS[splits.length];
        for (int i = 0; i < splits.length; i++) {
            String[] temp = StringUtils.split(splits[i], '/');
            words[i] = temp[0];
            poses[i] = POS.valueOf(temp[1]);
        }
    }

    public void addTermCombines(Multiset<String> termCombines) {
        add(termCombines, this.termCombines);
    }

    public Multiset<String> getTermCombines() {
        return termCombines;
    }

    /**
     * 计算上下文信息熵
     * 
     * @param stringFrequency
     * @param patArray
     * @return
     */
    private double calcEntropy(Multiset<String> adjacents) {
        if (adjacents.contains(Constant.UNKNOWN_LETTER) || adjacents.contains(Constant.UNKNOWN_PUNC)) {
            return Double.MAX_VALUE;
        }
        Set<String> elements = adjacents.elementSet();
        int total = 0;
        for (String element : elements) {
            total += adjacents.count(element);
        }
        double e = 0;
        for (String element : elements) {
            double p = (double) adjacents.count(element) / total;
            e -= p * Math.log(p);
        }
        return e;
    }

    public POS[] getPOSes() {

        if (poses == null) {
            calcMaxTermCombine();
        }
        return poses;
    }

    public void setPOSes(POS[] poses) {
        this.poses = poses;
    }

    public void addLeftAdjacents(Multiset<String> adjacents) {
        add(adjacents, leftAdjacents);
    }

    public Multiset<String> getLeftAdjacents() {
        return leftAdjacents;
    }

    public void addRightAdjacents(Multiset<String> adjacents) {
        add(adjacents, rightAdjacents);
    }

    public Multiset<String> getRightAdjacents() {
        return rightAdjacents;
    }

    private void add(Multiset<String> other, Multiset<String> adjacents) {
        Set<String> elements = other.elementSet();
        for (String element : elements) {
            adjacents.add(element, other.count(element));
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CandidateWord) {
            return Objects.equal(this.word, ((CandidateWord) obj).word);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.word);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
