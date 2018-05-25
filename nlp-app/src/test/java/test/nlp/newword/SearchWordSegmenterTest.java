package test.nlp.newword;

import java.io.IOException;
import java.util.LinkedList;

import org.junit.Test;

import test.nlp.common.Term;
import test.nlp.segment.IWordSegmenter;
import test.nlp.segment.Segmentations;
import test.nlp.segment.Segmentations.SegType;

public class SearchWordSegmenterTest extends WordSegmenterTest {

    @Override
    protected IWordSegmenter newWordSegmenter() {
        return Segmentations.getSegmenter(SegType.SEARCH);
    }

    @Test
    public void test() {
        LinkedList<Term> terms = wordSegmenter.segment("深圳“十二五”规划");
        for (Term term : terms) {
            System.out.println(term.getPos());
        }
        System.out.println(terms);
    }

    @Override
    public void testSegmentFile() throws IOException {

    }
}
