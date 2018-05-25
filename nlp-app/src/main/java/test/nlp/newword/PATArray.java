package test.nlp.newword;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import test.nlp.common.Term;
import test.nlp.segment.Segmentations;
import test.nlp.segment.Segmentations.SegType;
import test.nlp.util.StringUtils;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;

/**
 * PAT Array实现
 */
public abstract class PATArray {

    /**
     * 单字切分结果
     */
    protected Term[] terms;

    /**
     * array数组，每一个元素值为原始文本中的索引位置
     */
    protected Integer[] array;

    /**
     * lcP数组，lcp[i]表示array[i]与array[i+1]所表示的字符串的共有前缀或后缀
     */
    protected int[] lcp;

    protected int length;

    public PATArray(String text) {
        this(Segmentations.getSegmenter(SegType.SINGLE).segment(text).toArray(new Term[0]));
    }

    public PATArray(Term[] terms) {
        this.terms = terms;
        this.length = terms.length;
        this.array = new Integer[length];
        this.lcp = new int[length - 1];
        create();
    }

    /**
     * 获取邻接词的词频信息
     * 
     * @return
     */
    public Multiset<String> getAdjacents(int position) {
        Multiset<String> adjacents = HashMultiset.create();
        int minLength = lcp[position];

        addAjacent(adjacents, getAjacentTermIndex(position, minLength));
        for (int i = position; i < lcp.length; i++) {
            if (lcp[i] >= lcp[position]) {
                addAjacent(adjacents, getAjacentTermIndex(i + 1, minLength));
            } else {
                break;
            }
        }
        for (int i = position - 1; i >= 0; i--) {
            if (lcp[i] >= lcp[position]) {
                addAjacent(adjacents, getAjacentTermIndex(i, minLength));
            } else {
                break;
            }

        }

        return adjacents;
    }

    /**
     * 根据重复串信息获取term组合
     * 
     * @param stringFrequency
     * @return
     */
    public Multiset<String> getTermCombines(StringFrequency stringFrequency) {
        Multiset<String> termCombines = HashMultiset.create();
        StringBuilder key = new StringBuilder();
        for (int position : stringFrequency.getPositions()) {
            int startIndex = getStartIndex(position, lcp[stringFrequency.getPosition()]);
            int endIndex = startIndex + lcp[stringFrequency.getPosition()];
            for (int i = startIndex; i < endIndex; i++) {
                if (i > startIndex) {
                    key.append(" ");
                }
                key.append(terms[i].toString());
            }
            termCombines.add(key.toString());
            key.setLength(0);
        }
        return termCombines;
    }

    public String getWord(int position) {
        StringBuilder word = new StringBuilder();
        int startIndex = getStartIndex(position, lcp[position]);
        for (int i = startIndex, size = startIndex + lcp[position]; i < size; i++) {
            word.append(terms[i].getWord());
        }
        return word.toString();
    }

    /**
     * 扫描LCP数组获取所有的重复串信息
     * 
     * @param minFreq
     *            最小词频
     * @param minLength
     *            最小长度
     * @param maxLength
     *            最大长度
     * @return
     */
    public List<StringFrequency> scanLCP(int minFreq, int minLength, int maxLength) {
        List<StringFrequency> result = Lists.newArrayList();

        for (int i = 0; i < lcp.length;) {
            if (lcp[i] < minLength || lcp[i] > maxLength) {
                i++;
                continue;
            }

            int startIndex = findStartIndex(i);
            if (startIndex == -1) {
                i++;
                continue;
            }

            int j = startIndex;
            int temp = -1;
            List<Integer> positions = newArrayList();
            positions.add(j);
            for (; j < lcp.length; j++) {
                if (j > i && lcp[j] < lcp[i]) {
                    break;
                } else {
                    positions.add(j + 1);
                    if (j > i && lcp[j] > lcp[i] && temp == -1) {
                        temp = j;
                    }
                }
            }

            int freq = j - i + 1;
            if (freq >= minFreq) {
                result.add(new StringFrequency(i, positions));
            }

            if (temp != -1) {
                i = temp;
            } else {
                i = j;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("terms:[").append(ArrayUtils.toString(terms)).append("]")
                .append(System.getProperty("line.separator")).append("array:").append(Arrays.toString(array))
                .append(System.getProperty("line.separator")).append("lcp:").append(Arrays.toString(lcp));
        return builder.toString();
    }

    /**
     * 计算lcp
     */
    protected void calcLCP() {
        for (int i = 0; i < lcp.length; i++) {
            lcp[i] = getCommonLength(array[i], array[i + 1]);
        }
    }

    /**
     * 获取邻接词的索引位置
     * 
     * @param position
     *            重复串在array数组中的位置
     * @param length
     *            重复串长度
     * @return
     */
    protected abstract int getAjacentTermIndex(int position, int length);

    /**
     * 获取两个子串共有前缀或后缀的长度
     * 
     * @param offset1
     *            第一个子串的起始位置
     * @param offset2
     *            第二个子串的起始位置
     * @return
     */
    protected abstract int getCommonLength(int offset1, int offset2);

    /**
     * 获取重复串的起始位置
     * 
     * @param position
     *            重复串在array数组中的位置
     * @param length
     *            重复串的长度
     * @return
     */
    protected abstract int getStartIndex(int position, int length);

    /**
     * 创建比较器
     * 
     * @return
     */
    protected abstract Comparator<Integer> newComparator();

    /**
     * 添加邻接词
     * 
     * @param adjacents
     * @param index
     */
    private void addAjacent(Multiset<String> adjacents, int index) {

        if (index >= 0 && index < length) {
            String adjacentWord = terms[index].getWord();
            String unknownWord = StringUtils.getUnknownWord(terms[index].getPos());
            if (unknownWord == null) {
                adjacents.add(adjacentWord);
            } else {
                adjacents.add(unknownWord);
            }
        }

    }

    /**
     * 创建PAT Array
     */
    private void create() {
        for (int i = 0; i < length; i++) {
            array[i] = i;
        }
        Arrays.sort(array, newComparator());
        calcLCP();
    }

    private int findStartIndex(int index) {
        int startIndex = index;
        if (index - 1 >= 0 && lcp[index - 1] >= lcp[index]) {
            for (int k = index - 1; k >= 0; k--) {
                if (lcp[k] > lcp[index]) {
                    startIndex--;
                } else {
                    if (lcp[k] == lcp[index]) {
                        startIndex = -1;

                    }
                    break;
                }
            }
        }
        return startIndex;
    }

    public static void main(String[] args) {
        String text = "张学友张学友刘德华张学";
        LinkedList<Term> terms = Segmentations.getSegmenter(SegType.NLP).enableNameRecognize(false)
                .enablePlaceRecognize(false).segment(text);
        System.out.println(terms);
        PATArray patArray = new PrefixPATArray(terms.toArray(new Term[terms.size()]));
        System.out.println(ArrayUtils.toString(patArray.array));
        System.out.println(ArrayUtils.toString(patArray.lcp));
        List<StringFrequency> stringFrequencies = patArray.scanLCP(2, 2, 4);
        for (StringFrequency stringFrequency : stringFrequencies) {
            System.out.println(patArray.getWord(stringFrequency.getPosition()));
            Multiset<String> termCombines = patArray.getTermCombines(stringFrequency);
            System.out.println(termCombines);
        }

    }
}
