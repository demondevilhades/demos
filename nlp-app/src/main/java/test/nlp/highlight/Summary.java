package test.nlp.highlight;

import java.util.LinkedList;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * 摘要信息
 */
public class Summary {

    private List<Sentence> sentences = Lists.newArrayList();

    private float score = -1;

    private int maxScoreSentenceIndex;

    private int length;

    private String preTag;

    private String postTag;

    public Summary(String preTag, String postTag) {
        this.preTag = preTag;
        this.postTag = postTag;
    }

    public void addSentence(Sentence sentence) {
        this.sentences.add(sentence);
        if (score < sentence.getScore()) {
            this.score = sentence.getScore();
            maxScoreSentenceIndex = sentences.size() - 1;
            this.length = sentence.length();
        } else {
            this.length += sentence.length();
        }
    }

    /**
     * 获取摘要
     * 
     * @param charArray
     * @return
     */
    public String getSummary(char[] charArray, int limit) {

        LinkedList<String> list = Lists.newLinkedList();
        int startIndex = maxScoreSentenceIndex;
        int remaining = limit - length;
        while (remaining > 0 && startIndex > 0) {
            startIndex--;
            Sentence sentence = sentences.get(startIndex);
            if (sentence.length() <= remaining) {
                list.addFirst(sentence.generate(charArray, preTag, postTag, sentence.length()));
                remaining -= sentence.length();
            } else {
                list.add(sentence.generate(charArray, preTag, postTag, remaining));
                break;
            }
        }

        remaining = limit;
        for (int index = maxScoreSentenceIndex, size = sentences.size(); index < size; index++) {
            Sentence sentence = sentences.get(index);
            list.add(sentence.generate(charArray, preTag, postTag, Math.min(sentence.length(), remaining)));
            remaining -= sentence.length();
            if (remaining <= 0) {
                break;
            }
        }

        StringBuilder builder = new StringBuilder();
        for (String temp : list) {
            builder.append(temp);
        }
        return builder.toString();
    }

    /**
     * 计算从最匹配句子开始向后的长度
     * 
     * @return
     */
    public int getLength() {
        return length;
    }

    public int getMaxScoreSentenceIndex() {
        return maxScoreSentenceIndex;
    }

    public float getScore() {
        return score;
    }

    public static class Sentence {
        private int start;

        private int end;

        private int[] highlightTags;

        private int[] hits;

        /**
         * 第一个高亮的位置
         */
        private int firstHighlightIndex = Integer.MAX_VALUE;

        private int lastHighlightIndex = -1;

        private float score;

        public Sentence(int start, int size, int tokenNum) {
            this.start = start;
            this.end = start + size;
            highlightTags = new int[size];
            this.hits = new int[tokenNum];
        }

        public void addHit(int index) {
            hits[index]++;
        }

        public void setHighlightTags(int start, int end) {
            firstHighlightIndex = Math.min(start, firstHighlightIndex);
            if (end <= lastHighlightIndex + 1) {
                return;
            }
            for (int i = Math.max(lastHighlightIndex + 1, start); i < end; i++) {
                highlightTags[i] = 1;

            }
            lastHighlightIndex = end - 1;
        }

        public float getScore() {
            if (score == 0) {
                calcScore();
            }
            return score;
        }

        public String generate(char[] charArray, String preTag, String postTag, int length) {

            boolean startTag = false;
            int start = 0;
            if (highlightTags.length > length && lastHighlightIndex != -1 && lastHighlightIndex - start + 1 > length) {
                if (highlightTags.length - firstHighlightIndex <= length) {
                    start = highlightTags.length - length;
                } else if (lastHighlightIndex - firstHighlightIndex + 1 <= length) {
                    start = lastHighlightIndex + 1 - length;
                } else {
                    start = firstHighlightIndex;
                }
            }

            StringBuilder builder = new StringBuilder();
            int end = start + length;
            for (int i = start; i < end; i++) {
                if (highlightTags[i] == 1 && !startTag) {
                    builder.append(preTag);
                    startTag = true;
                } else if (highlightTags[i] == 0 && startTag) {
                    builder.append(postTag);
                    startTag = false;
                }
                builder.append(charArray[this.start + i]);
            }
            if (highlightTags[end - 1] == 1) {
                builder.append(postTag);
            }
            if (end != highlightTags.length) {
                builder.append("...");
            }
            return builder.toString();
        }

        public int length() {
            return end - start;
        }

        /**
         * 计算句子的得分，对于每一个词，命中一次得1分，其后每次命中加0.01分
         */
        private void calcScore() {
            float score = 0;
            for (int i = 0; i < hits.length; i++) {
                if (hits[i] > 0) {
                    score += 1 + (hits[i] - 1) * 0.01;
                }
            }
            this.score = score;
        }
    }
}
