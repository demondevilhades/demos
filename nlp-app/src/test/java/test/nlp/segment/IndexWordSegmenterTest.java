package test.nlp.segment;

import org.junit.Test;

import test.nlp.dict.custom.CustomDictionary;
import test.nlp.dict.pos.POS;
import test.nlp.newword.WordSegmenterTest;
import test.nlp.segment.Segmentations.SegType;

public class IndexWordSegmenterTest extends WordSegmenterTest {

    @Override
    protected IWordSegmenter newWordSegmenter() {
        return Segmentations.getSegmenter(SegType.INDEX, "irobot").enableNumberRecognize(true);
    }

    @Test
    public void test() {
        CustomDictionary.getInstance().addWord("新东方", POS.n, 1, "irobot");
        CustomDictionary.getInstance().addWord("东方市场", POS.n, 1, "irobot");
        System.out.println(wordSegmenter.segment("新东方市场"));
    }
}
