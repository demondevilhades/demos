package test.nlp.segment;

import java.util.LinkedList;

import com.google.common.collect.Lists;
import test.nlp.common.Sentence;
import test.nlp.common.Term;
import test.nlp.common.Vertex;
import test.nlp.dict.med.MedDictionary;

/**
 * med
 * 
 * @author zs
 */
public class MedWordSegmenter extends ViterbiWordSegmenter {

    protected MedDictionary medDictionary;

    public MedWordSegmenter(String domain) {
        super(domain);
        enablePOSTagger(true);
        enableNumberRecognize(true);
        enableNameRecognize(false);
        enablePlaceRecognize(false);
        medDictionary = MedDictionary.getInstance();
    }

    @Override
    protected LinkedList<Term> getResult(Sentence sentence, LinkedList<Vertex> optimizeVertexes, Graph optimizeGraph,
            Graph graph) {
        Vertex[] vertexes = optimizeVertexes.toArray(new Vertex[optimizeVertexes.size()]);
        medDictionary.fmm(vertexes, domain);
        LinkedList<Term> terms = Lists.newLinkedList();
        for (Vertex vertex : vertexes) {
            if (vertex == null) {
                continue;
            }
            terms.add(createTerm(sentence.getOffset(), vertex));
        }
        return terms;
    }
}
