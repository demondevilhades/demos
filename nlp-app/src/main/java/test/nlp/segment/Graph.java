package test.nlp.segment;

import java.util.LinkedList;
import java.util.ListIterator;

import com.google.common.collect.Lists;
import test.nlp.Constant;
import test.nlp.common.Vertex;
import test.nlp.dict.CoreDictionary;
import test.nlp.dict.pos.POS;

/**
 * 分词图表
 */
public class Graph {

    private LinkedList<Vertex>[] vertexes;

    private static Vertex begin = new Vertex(-1, Constant.SENTENCE_BEGIN, CoreDictionary.getInstance()
            .getUnknownPOSAttr(Constant.SENTENCE_BEGIN, new POS[] { POS.begin }));

    private static Vertex end = new Vertex(-1, Constant.SENTENCE_END, CoreDictionary.getInstance().getUnknownPOSAttr(
            Constant.SENTENCE_END, new POS[] { POS.end }));

    /**
     * 顶点数目
     */
    private int vertexNum;

    @SuppressWarnings("unchecked")
    public Graph(int size) {
        vertexes = new LinkedList[size + 1];
        for (int i = 0; i < vertexes.length; i++) {
            vertexes[i] = Lists.newLinkedList();
        }

        vertexes[size].add((Vertex) end.clone());
    }

    /**
     * 根据初次优化结果构造分词图表
     * 
     * @param size
     * @param vertexes
     */
    @SuppressWarnings("unchecked")
    public Graph(LinkedList<Vertex> optimizeVertexes, Graph graph) {
        vertexes = new LinkedList[graph.vertexes.length];
        for (int i = 0; i < vertexes.length; i++) {
            vertexes[i] = Lists.newLinkedList();
        }

        for (Vertex vertex : optimizeVertexes) {
            vertexes[vertex.getIndex()].add(vertex);
            vertexNum++;
        }
        vertexes[vertexes.length - 1].add((Vertex) end.clone());
    }

    public int getVertexNum() {
        return vertexNum;
    }

    public LinkedList<Vertex>[] getVertexes() {
        return vertexes;
    }

    /**
     * 添加顶点至某一行的首部
     * 
     * @param vertex
     */
    public void addFirst(Vertex vertex) {
        vertexes[vertex.getIndex()].addFirst(vertex);
    }

    /**
     * 添加顶点至某一行的尾部
     * 
     * @param vertex
     */
    public void addLast(Vertex vertex) {
        vertexes[vertex.getIndex()].addLast(vertex);
    }

    /**
     * 新增一个顶点
     * 
     * @param vertex
     */
    public boolean addVertex(Vertex vertex) {
        LinkedList<Vertex> rowVertexes = vertexes[vertex.getIndex()];

        int length = vertex.length();
        if (rowVertexes.isEmpty() || rowVertexes.getFirst().length() < length) {
            rowVertexes.addFirst(vertex);
        } else if (rowVertexes.getLast().length() > length) {
            rowVertexes.addLast(vertex);
        } else {
            ListIterator<Vertex> iterator = rowVertexes.listIterator();
            while (iterator.hasNext()) {
                Vertex curVertex = iterator.next();
                if (curVertex.length() == length) {
                    return false;
                }
                if (curVertex.length() < length) {
                    iterator.previous();
                    iterator.add(vertex);
                    break;
                }
            }
        }
        vertexNum++;
        return true;
    }

    /**
     * 新增一个顶点信息
     * 
     * @param vertex
     * @param graph
     * @return
     */
    public boolean addVertex(Vertex vertex, Graph graph) {
        if (!addVertex(vertex)) {
            return false;
        }

        for (int row = vertex.getIndex() - 1; row >= 0; row--) {
            if (vertexes[row].isEmpty() || vertexes[row].getLast().length() > 1) {
                Vertex temp = graph.getLast(row);
                if (temp == null) {
                    break;
                }
                vertexes[row].addLast(temp);
                vertexNum++;
                if (vertexes[row].size() > 1) {
                    break;
                }
            }
        }

        int row = vertex.getIndex() + vertex.length();
        if (vertexes[row].isEmpty()) {
            LinkedList<Vertex> tempList = graph.getVertexes(row);
            if (tempList.isEmpty()) {
                return true;
            }
            vertexes[row].addAll(tempList);
            vertexNum += tempList.size();
        }

        for (row++; row < vertexes.length; row++) {
            if (vertexes[row].isEmpty()) {
                Vertex temp = graph.getLast(row);
                if (temp == null) {
                    break;
                }
                vertexes[row].addLast(temp);
                vertexNum++;
            } else {
                break;
            }
        }
        return true;
    }

    /**
     * 获取最后一行的顶点信息
     * 
     * @return
     */
    public LinkedList<Vertex> getLastRow() {
        return vertexes[vertexes.length - 1];
    }

    /**
     * 获取指定行的顶点信息
     * 
     * @param row
     * @return
     */
    public LinkedList<Vertex> getVertexes(int row) {
        return vertexes[row];
    }

    /**
     * 获取指定行的最后一个顶点
     * 
     * @param row
     * @return
     */
    public Vertex getLast(int row) {
        return vertexes[row].isEmpty() ? null : vertexes[row].getLast();
    }

    public void removeVertex(Vertex vertex) {
        for (Vertex toVertex : vertexes[vertex.getIndex() + vertex.length()]) {
            if (toVertex.getFrom() == vertex) {
                toVertex.setFrom(null);
            }
        }
        vertexes[vertex.getIndex()].remove(vertex);
    }

    /**
     * 利用viterbi算法消歧
     * 
     * @return
     */
    public LinkedList<Vertex> viterbi() {
        calcScores(begin, 0);
        for (int i = 0, length = vertexes.length - 1; i < length; i++) {
            if (vertexes[i] != null) {
                for (Vertex vertex : vertexes[i]) {
                    calcScores(vertex, i + vertex.length());
                }
            }
        }
        return optimize();
    }

    /**
     * 计算得分
     * 
     * @param from
     *            起始词
     * @param to
     *            终止词位置
     * @param endPosition
     *            结束位置
     */
    private void calcScores(Vertex from, int to) {
        if (vertexes[to] != null) {
            for (Vertex vertex : vertexes[to]) {
                vertex.updateFrom(from);
            }
        }
    }

    /**
     * 获取消歧结果
     * 
     * @param startPosition
     *            起始位置
     * @param endPosition
     *            结束位置
     * 
     * @return
     */
    private LinkedList<Vertex> optimize() {
        LinkedList<Vertex> optimizeVertexes = Lists.newLinkedList();
        Vertex vertex = vertexes[vertexes.length - 1].getFirst();
        Vertex from = null;
        while ((from = vertex.getFrom()) != begin) {
            optimizeVertexes.addFirst(from);
            vertex = from;
        }
        return optimizeVertexes;
    }

}
