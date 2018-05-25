package test.nlp.keyword;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Comparator;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

/**
 * 关键词信信息
 */
public class Keyword implements Comparable<Keyword> {

    private static DecimalFormat decimalFormat = new DecimalFormat("0.0000");

    static {
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
    }

    /**
     * 关键词
     */
    private String word;

    /**
     * 词频
     */
    private int freq;

    /**
     * 权重
     */
    private double weight;

    public Keyword(String word, int freq, double weight) {
        this.word = word;
        this.freq = freq;
        this.weight = weight;
    }

    public String getFormatWeight() {
        return decimalFormat.format(weight);
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getWord() {
        return word;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Keyword) {
            return Objects.equal(this.word, ((Keyword) obj).word);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(word).toHashCode();
    }

    /**
     * 默认是按关键词排序
     */
    @Override
    public int compareTo(Keyword o) {
        return this.word.compareTo(o.word);
    }

    @Override
    public String toString() {
        return word + "|" + weight;
    }

    /**
     * 关键词权重比较器（按权重降序排序）
     */
    public static class WeightComparator implements Comparator<Keyword> {

        @Override
        public int compare(Keyword o1, Keyword o2) {
            if (o1.equals(o2)) {
                return 0;
            }
            return ComparisonChain.start().compare(o2.weight, o1.weight).compare(o1.word, o2.word).result();
        }
    }

}
