package test.nlp.util;

import java.util.Collection;

import test.nlp.Constant;
import test.nlp.common.Vertex;
import test.nlp.dict.BigramDictionary;
import test.nlp.dict.CoreDictionary;

public class MathUtil {

    // 平滑参数
    private static final double dSmoothingPara = 0.1;

    // ﻿Two linked Words frequency
    private static final double dTemp = (double) 1 / CoreDictionary.TOTAL_FREQ;

    public static double computeVertexScore(Vertex from, Vertex to) {
        double freq = from.getTotalFreq();
        if (freq == 0) {
            freq = CoreDictionary.getInstance().getFreq(from.getText());
        }

        int twoWordsFreq = BigramDictionary.getInstance().getTransitionFreq(from.getText(), to.getText());
        double value = -Math.log(dSmoothingPara * (1 + freq) / CoreDictionary.TOTAL_FREQ + (1 - dSmoothingPara)
                * ((1 - dTemp) * twoWordsFreq / (1 + freq) + dTemp));

        return Math.abs(value);
    }

    /**
     * 计算互信息
     * 
     * @param bigram
     *            bigram的频率
     * @param first
     *            第一个词的频率
     * @param second
     *            第二个词的频率
     * @return
     */
    public static double calcMI(double bigram, double first, double second) {
        return Math.log(bigram) - Math.log(first) - Math.log(second);
    }

    /**
     * 计算互信息
     * 
     * @param freq1
     *            第一个词出现的次数
     * @param freq2
     *            第二个词出现的次数
     * @param dFreq
     *            两个词一起出现的次数
     * @param totalFreq
     *            词总数
     * @return
     */
    public static double calcMI(int freq1, int freq2, int dFreq, int totalFreq) {
        return Math.log(Math.max(Constant.MIN_PROB, dFreq * 1.0 / totalFreq))
                - Math.log(Math.max(Constant.MIN_PROB, freq1 * 1.0 / totalFreq))
                - Math.log(Math.max(Constant.MIN_PROB, freq2 * 1.0 / totalFreq));
    }

    /**
     * 计算互信息
     * 
     * @param freq1
     *            第一个词在语料库中出现的次数
     * @param freq2
     *            第二个词在语料库中出现的次数
     * @param bigramFreq
     *            两个词在文本中的共现次数
     * @param totalBigram
     *            文本中的总的bigram数
     * @param totalFreq
     *            语料库中的总词频
     * @return
     */
    public static double calcMI(int freq1, int freq2, int bigramFreq, int totalBigram, int totalFreq) {
        return Math.log(Math.max(Constant.MIN_PROB, (double) bigramFreq / totalBigram))
                - Math.log(Math.max(Constant.MIN_PROB, (double) freq1 / totalFreq))
                - Math.log(Math.max(Constant.MIN_PROB, (double) freq2 / totalFreq));
    }

    /**
     * 计算似然比
     * 
     * @param freq1
     *            第一个词出现的次数
     * @param freq2
     *            第二个词出现的次数
     * @param dFreq
     *            两个词一起出现的次数
     * @param totalFreq
     *            词总数
     * @return
     */
    public static double calcLLR(int freq1, int freq2, int dFreq, int totalFreq) {
        assert (freq1 != 0 && freq2 != 0 && dFreq != 0 && totalFreq != 0);
        double score = calcLLR(dFreq * 1.0 / freq1, dFreq, freq1);
        score += calcLLR((freq2 - dFreq) * 1.0 / (totalFreq - freq1), freq2 - dFreq, totalFreq - freq1);
        score -= calcLLR(freq2 * 1.0f / totalFreq, dFreq, freq1);
        score -= calcLLR(freq2 * 1.0f / totalFreq, freq2 - dFreq, totalFreq - freq1);
        return score;
    }

    private static double calcLLR(double p, int k, int n) {
        if (p == 0 || p == 1) {
            return 0;
        }
        return k * Math.log(p) + (n - k) * Math.log(1 - p);
    }

    public static double guass(long origin, long offset, double scale, double decay, long current) {
        scale = 0.5 * Math.pow(scale, 2.0) / Math.log(decay);
        return Math.exp(0.5 * Math.pow(Math.max(0.0d, Math.abs(current - origin) - offset), 2.0) / scale);
    }

    /**
     * 时间降权函数
     * 
     * @param region
     * @param offset
     * @param decay
     * @param scale
     */
    public static double gaussDecayFunction(long region, long offset, long scale, double decay, long value) {
        long distance = Math.max(Math.abs(value - region) - offset, 0);
        return Math.exp(0.5 * Math.pow(distance, 2.0) / (0.5 * Math.pow(scale, 2.0) / Math.log(decay)));
    }

    /**
     * 求和
     * 
     * @param values
     * @return
     */
    public static double sum(Collection<Double> values) {
        double result = 0;
        for (double value : values) {
            result += value;
        }
        return result;
    }

    /**
     * 计算jacard系数
     * 
     * @param nrMatch
     * @param gramNum1
     * @param gramNum2
     * @return
     */
    public static double calcJacard(int nrMatch, int gramNum1, int gramNum2) {

        return nrMatch * 1.0 / (gramNum1 + gramNum2 - nrMatch);
    }

    /**
     * 返回笛卡尔积
     * 
     * @param preArray
     * @param array
     * @return
     */
    public static String[] getDescartesResult(String[] preArray, String[] array) {
        if (preArray == null) {
            return array;
        }

        if (array.length == 1) {
            return ArrayUtils.append(preArray, array[0]);
        }

        int m = preArray.length;
        int n = array.length;
        String[] result = new String[m * n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result[i * n + j] = preArray[i] + array[j];
            }
        }
        return result;
    }

    /**
     * 返回笛卡尔积
     * 
     * @param preArray
     * @param collections
     * @return
     */
    public static String[] getDescartesResult(String[] preArray, Collection<String> collections) {
        if (preArray == null) {
            return collections.toArray(new String[collections.size()]);
        }
        if (collections.size() == 1) {
            return ArrayUtils.append(preArray, collections.iterator().next());
        }

        int m = preArray.length;
        int n = collections.size();
        String[] result = new String[m * n];

        for (int i = 0; i < m; i++) {
            int index = 0;
            for (String appendStr : collections) {
                result[i * n + index] = preArray[i] + appendStr;
                index++;
            }
        }
        return result;
    }

    /**
     * 计算jarcard系数
     * 
     * @param x
     *            集合x中的元素个数
     * @param y
     *            集合y中的元素个数
     * @param xy
     *            x与y的交集中的元素个数
     * @return
     */
    public static double computeJarcard(int x, int y, int xy) {
        return xy * 1.0 / (x + y - xy);
    }

    public static void main(String[] args) {
        System.out.println(calcMI(40, 12040, 14, CoreDictionary.TOTAL_FREQ + 14));
    }

}
