package test.nlp.util;

import java.util.Iterator;
import java.util.LinkedList;

import com.google.common.collect.Lists;
import test.nlp.Constant;
import test.nlp.common.Vertex;
import test.nlp.dict.CoreDictionary;
import test.nlp.dict.POSTransformMatrixDictionary;
import test.nlp.dict.TransformMatrixDictionary;
import test.nlp.dict.pos.POS;
import test.nlp.segment.VertexAttribute;

/**
 * 维特比算法
 */
public class Viterbi {

    /**
     * 利用Vertibi算法计算出最终的角色属性
     * 
     * @param attrs
     * @return 返回标注结果
     */
    public static <E extends Enum<E>> LinkedList<E> compute(LinkedList<VertexAttribute<E>> vertexAttrs,
            VertexAttribute<E> begin, TransformMatrixDictionary<E> transformMatrixDictionary) {
        /**
         * 计算得分
         */
        computeScore(vertexAttrs, begin, transformMatrixDictionary);
        return getBestTag(vertexAttrs);
    }

    /**
     * 利用Vertibi算法计算出最终的词性
     * 
     * @param vertexes
     * @param transformMatrixDictionary
     */
    public static void computePOS(LinkedList<Vertex> vertexes) {
        LinkedList<VertexAttribute<POS>> vertexAttributes = Lists.newLinkedList();
        for (Vertex vertex : vertexes) {
            vertexAttributes.add(new VertexAttribute<POS>(vertex, vertex.getPosAttr()));
        }
        vertexAttributes.add(new VertexAttribute<POS>(CoreDictionary.getInstance().getUnknownPOSAttr(
                Constant.SENTENCE_END, new POS[] { POS.end })));

        computeScore(
                vertexAttributes,
                new VertexAttribute<POS>(CoreDictionary.getInstance().getUnknownPOSAttr(Constant.SENTENCE_BEGIN,
                        new POS[] { POS.begin })), POSTransformMatrixDictionary.getInstance());
        getBestPOSTag(vertexAttributes);
    }

    private static <E extends Enum<E>> void computeScore(LinkedList<VertexAttribute<E>> vertexAttrs,
            VertexAttribute<E> begin, TransformMatrixDictionary<E> transformMatrixDictionary) {
        Iterator<VertexAttribute<E>> iterator = vertexAttrs.iterator();
        VertexAttribute<E> prev = begin;
        VertexAttribute<E> cur = null;
        while (iterator.hasNext()) {
            cur = iterator.next();
            if (!cur.isEmpty()) {
                cur.setFromAndScore(prev, transformMatrixDictionary);
                prev = cur;
            }
        }
    }

    private static void getBestPOSTag(LinkedList<VertexAttribute<POS>> vertexAttributes) {
        int bestIndex = vertexAttributes.pollLast().getFromIndex(0);
        while (!vertexAttributes.isEmpty()) {
            VertexAttribute<POS> vertexAttribute = vertexAttributes.pollLast();
            if (vertexAttribute.isEmpty()) {
                continue;
            }
            vertexAttribute.getVertex().setPos(vertexAttribute.getItem(bestIndex));
            bestIndex = vertexAttribute.getFromIndex(bestIndex);
        }
    }

    private static <E extends Enum<E>> LinkedList<E> getBestTag(LinkedList<VertexAttribute<E>> vertexAttrs) {
        LinkedList<E> res = Lists.newLinkedList();
        int bestIndex = vertexAttrs.pollLast().getFromIndex(0);
        while (!vertexAttrs.isEmpty()) {
            VertexAttribute<E> vertexAttribute = vertexAttrs.pollLast();
            if (vertexAttribute.isEmpty()) {
                continue;
            }
            res.addFirst(vertexAttribute.getItem(bestIndex));
            bestIndex = vertexAttribute.getFromIndex(bestIndex);
        }
        return res;
    }
}
