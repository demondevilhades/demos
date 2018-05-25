package test.nlp.segment;

import java.util.LinkedList;
import java.util.ListIterator;

import com.google.common.collect.Lists;
import test.nlp.Constant;
import test.nlp.common.Sentence;
import test.nlp.common.Term;
import test.nlp.common.Vertex;
import test.nlp.dict.CoreDictionary;
import test.nlp.dict.pos.POS;
import test.nlp.util.StringUtils;
import test.nlp.util.Viterbi;

/**
 * Viterbi分词
 */
public class ViterbiWordSegmenter extends BaseWordSegmenter {

    public ViterbiWordSegmenter(String domain) {
        super(domain);
    }

    @Override
    public LinkedList<Term> segment(Sentence sentence) {
        Graph graph = generateGraph(sentence);
        LinkedList<Vertex> optimizeVertexes = graph.viterbi();

        Graph optimizeGraph = new Graph(optimizeVertexes, graph);
        if (config.isEnableNER()) {
            if (config.isEnableNameRecognize()) {
                PersonRecognition.personRecognize(optimizeVertexes, graph, optimizeGraph);
            }
            if (config.isEnablePlaceRecognize()) {
                PlaceRecognition.placeRecognize(optimizeVertexes, graph, optimizeGraph);
            }
            if (optimizeVertexes.size() != optimizeGraph.getVertexNum()) {
                optimizeVertexes = optimizeGraph.viterbi();
            }
        }
        if (config.isEnablePOSTagger() || config.isEnableNumberRecognize()) {
            Viterbi.computePOS(optimizeVertexes);
            if (config.isEnableNumberRecognize() && optimizeVertexes.size() > 1) {
                merge(optimizeVertexes, graph);
            }
        }

        return getResult(sentence, optimizeVertexes, optimizeGraph, graph);
    }

    protected LinkedList<Term> getResult(Sentence sentence, LinkedList<Vertex> optimizeVertexes, Graph optimizeGraph,
            Graph graph) {
        Vertex[] vertexes = optimizeVertexes.toArray(new Vertex[optimizeVertexes.size()]);
        customDictionary.fmm(vertexes, domain);
        LinkedList<Term> terms = Lists.newLinkedList();
        for (Vertex vertex : vertexes) {
            if (vertex == null) {
                continue;
            }
            terms.add(createTerm(sentence.getOffset(), vertex));
        }
        return terms;
    }

    /**
     * 合并数词
     * 
     * @param vertexes
     */
    private void merge(LinkedList<Vertex> vertexes, Graph graph) {

        ListIterator<Vertex> iterator = vertexes.listIterator();

        StringBuilder merge = new StringBuilder();
        POS pos = null;
        String text = null;
        while (iterator.hasNext()) {
            Vertex curVertex = iterator.next();

            Vertex nextVertex = null;

            if (POS.m == curVertex.getPos()) {
                pos = POS.m;
                merge.setLength(0);
                merge.append(curVertex.getWord());
                while (iterator.hasNext()) {
                    nextVertex = iterator.next();
                    if (nextVertex.getPos() == null) {
                        break;
                    }
                    if (POS.m == nextVertex.getPos()) {
                        graph.removeVertex(nextVertex);
                        merge.append(nextVertex.getWord());
                        iterator.remove();
                    } else {
                        String word = merge.toString();
                        if (isTime(word, nextVertex.getWord())) {
                            graph.addLast(new Vertex(curVertex.getIndex(), word, Constant.UNKNOWN_NUM, CoreDictionary
                                    .getInstance().getUnknownPOSAttr(Constant.UNKNOWN_NUM, new POS[] { pos })));
                            merge.append(nextVertex.getWord());
                            text = Constant.UNKNOWN_TIME;
                            pos = POS.t;
                            iterator.remove();
                        } else if (nextVertex.getPos().isBelong(POS.q)) {
                            graph.addLast(new Vertex(curVertex.getIndex(), word, Constant.UNKNOWN_NUM, CoreDictionary
                                    .getInstance().getUnknownPOSAttr(Constant.UNKNOWN_NUM, new POS[] { pos })));
                            merge.append(nextVertex.getWord());
                            text = Constant.UNKNOWN_NUM;
                            pos = POS.mq;
                            iterator.remove();
                        }
                        break;
                    }
                }

                if (merge.length() != curVertex.getWord().length()) {
                    curVertex.setWord(merge.toString());
                    curVertex.setText(text);
                    curVertex.setPos(pos);
                    curVertex.setPosAttr(CoreDictionary.getInstance().getUnknownPOSAttr(Constant.UNKNOWN_NUM,
                            new POS[] { pos }));
                }
            }

        }

    }

    /**
     * 判断两个词是不是可以合并为时间词
     * 
     * @param prevWord
     * @param curWord
     * @return
     */
    private boolean isTime(String prevWord, String curWord) {

        boolean isTime = false;
        if ("年".equals(curWord) && StringUtils.isYearTime(prevWord)) {
            isTime = true;
        } else if (("月".equals(curWord) || "月份".equals(curWord)) && StringUtils.isMonthTime(prevWord)) {
            isTime = true;
        } else if ("日".equals(curWord) && StringUtils.isDayTime(prevWord)) {
            isTime = true;
        } else if ("时".equals(curWord) && StringUtils.isHourTime(prevWord)) {
            isTime = true;
        }
        return isTime;
    }
}
