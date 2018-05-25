package test.nlp.util;

import java.util.List;

import test.nlp.keyword.Keyword;

public class KeywordUtil {

    /**
     * 获取特征词向量的模
     * 
     * @param keywords
     * @return
     */
    public static float getNorm(List<Keyword> keywords) {
        float norm = 0;
        for (Keyword keyword : keywords) {
            norm += (float) Math.pow(keyword.getWeight(), 2);
        }

        return (float) Math.sqrt(norm);
    }

    /**
     * 对各特征词的权重进行归一化
     * 
     * @param keywords
     * @param lengthNorm
     */
    public static void normalize(List<Keyword> keywords, double lengthNorm) {
        for (Keyword keyword : keywords) {
            keyword.setWeight(keyword.getWeight() / lengthNorm);
        }
    }

    public static double sim(List<Keyword> keywords1, List<Keyword> keywords2) {
        return sim(keywords1, getNorm(keywords1), keywords2, getNorm(keywords2));
    }

    /**
     * 计算相似度
     * 
     * @param keywords1
     * @param norm1
     * @param keywords2
     * @param norm2
     */
    public static double sim(List<Keyword> keywords1, double norm1, List<Keyword> keywords2, double norm2) {
        double sim = 0;
        for (int i = 0, j = 0; i < keywords1.size() && j < keywords2.size();) {
            Keyword keyword1 = keywords1.get(i);
            Keyword keyword2 = keywords2.get(j);
            int compare = keyword1.compareTo(keyword2);
            if (compare == 0) {
                sim += keyword1.getWeight() * keyword2.getWeight();
                i++;
                j++;
            } else if (compare < 0) {
                i++;
            } else {
                j++;
            }
        }
        return sim / (norm1 * norm2);
    }
}
