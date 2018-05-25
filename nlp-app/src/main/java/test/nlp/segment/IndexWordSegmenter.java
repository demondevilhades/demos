package test.nlp.segment;

import java.util.LinkedList;

import com.google.common.collect.Lists;
import test.nlp.common.Sentence;
import test.nlp.common.Term;
import test.nlp.common.Vertex;

/**
 * 索引分词
 */
public class IndexWordSegmenter extends ViterbiWordSegmenter {

    public IndexWordSegmenter(String domain) {
        super(domain);
    }

    @Override
    protected LinkedList<Term> getResult(Sentence sentence, LinkedList<Vertex> optimizeVertexes, Graph optimizeGraph,
            Graph graph) {
        customDictionary.segment(sentence, graph, domain);
        LinkedList<Term> res = Lists.newLinkedList();
        LinkedList<Vertex>[] vertexes = graph.getVertexes();
        for (int i = 0; i < vertexes.length - 1; i++) {
            if (vertexes[i].isEmpty()) {
                continue;
            }
            for (Vertex vertex : vertexes[i]) {
                res.add(createTerm(sentence.getOffset(), vertex));
            }
        }
        return res;
    }

}
