package test.nlp.segment;

import test.nlp.Constant;
import test.nlp.common.Attribute;
import test.nlp.common.Vertex;
import test.nlp.dict.TransformMatrixDictionary;

public class VertexAttribute<E extends Enum<E>> {

    private Attribute<E> attribute;

    private Vertex vertex;

    private double[] scores;

    private int[] fromIndexes;

    private int size;

    public VertexAttribute(Vertex vertex, Attribute<E> attribute) {
        this(attribute);
        this.vertex = vertex;
    }

    public VertexAttribute(Attribute<E> attribute) {
        this.attribute = attribute;
        this.scores = new double[attribute.size()];
        this.fromIndexes = new int[attribute.size()];
        this.size = attribute.size();
    }

    public Vertex getVertex() {
        return vertex;
    }

    public E getItem(int index) {
        return attribute.getItem(index);
    }

    public int getFromIndex(int index) {
        return fromIndexes[index];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void setFromAndScore(VertexAttribute<E> from, TransformMatrixDictionary<E> transformMatrixDictionary) {
        for (int i = 0; i < from.size; i++) {
            for (int j = 0; j < size; j++) {
                double score = from.scores[i]
                        + calcScore(from.attribute.getItem(i), from.attribute.getFreq(i), attribute.getItem(j),
                                attribute.getFreq(j), transformMatrixDictionary);
                if (scores[j] == 0 || scores[j] > score) {
                    fromIndexes[j] = i;
                    scores[j] = score;
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            if (builder.length() > 0) {
                builder.append("\n");
            }
            builder.append(attribute.getItem(i) + ":" + scores[i]);
        }
        return builder.toString();
    }

    /**
     * 计算得分
     * 
     * @param from
     * @param fromFreq
     * @param to
     * @param toFreq
     * @return
     */
    private double calcScore(Enum<E> from, int fromFreq, Enum<E> to, int toFreq,
            TransformMatrixDictionary<E> transformMatrixDictionary) {
        int transitionFreq = transformMatrixDictionary.getTransitionFreq(from, to);
        int fromTotalFreq = transformMatrixDictionary.getFreq(from);
        int toTotalFreq = transformMatrixDictionary.getFreq(to);

        if (fromTotalFreq == 0 || toTotalFreq == 0) {
            return -Math.log(Constant.MIN_PROB);
        }

        double score = -Math.log((transitionFreq + 1) * 1.0 / fromTotalFreq);
        score -= Math.log(toFreq * 1.0 / toTotalFreq);
        return score;
    }

    public static void main(String[] args) {
        System.out.println(-Math.log(0.3));
        System.out.println(-Math.log(0.5));

    }
}
