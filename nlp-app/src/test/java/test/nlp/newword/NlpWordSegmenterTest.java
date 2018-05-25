package test.nlp.newword;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import test.nlp.common.Term;
import test.nlp.dict.custom.CustomDictionary;
import test.nlp.dict.pos.POS;
import test.nlp.region.Region;
import test.nlp.region.Region.LocationType;
import test.nlp.region.Regions;
import test.nlp.segment.IWordSegmenter;
import test.nlp.segment.Segmentations;
import test.nlp.segment.Segmentations.SegType;

import com.google.common.collect.Lists;

public class NlpWordSegmenterTest extends WordSegmenterTest {
    private Regions regions;

    @Before
    public void setup() {
        this.regions = initRegions();
        CustomDictionary.getInstance().addWord("福田区", POS.nst, 1, "irobot");
        CustomDictionary.getInstance().addWord("福田", POS.nst, 1, "irobot");
        CustomDictionary.getInstance().addWord("南园街道", POS.ns, 1, "irobot");
        CustomDictionary.getInstance().addWord("南园", POS.ns, 1, "irobot");
        CustomDictionary.getInstance().addWord("园岭街道", POS.ns, 1, "irobot");
        CustomDictionary.getInstance().addWord("园岭", POS.ns, 1, "irobot");
        CustomDictionary.getInstance().addWord("园东社区", POS.ns, 1, "irobot");
        CustomDictionary.getInstance().addWord("园东", POS.ns, 1, "irobot");
    }

    @Override
    protected IWordSegmenter newWordSegmenter() {
        return Segmentations.getSegmenter(SegType.NLP, "irobot").enableNameRecognize(false).enablePlaceRecognize(false);
    }

    @Test
    public void test() {
        LinkedList<Term> terms = wordSegmenter.segment("广东省深圳市福田区南园园东社区");
        this.regions.merge(this.regions.getRegionByCode("440300"), terms);
        for (Term term : terms) {
            System.out.println(term);
        }
    }

    private Regions initRegions() {
        List<Region> regions = Lists.newArrayList();
        regions.add(new Region("440000", "广东省", "广东", LocationType.PROVINCE));
        regions.add(new Region("440300", "深圳市", "深圳", LocationType.CITY, "440000"));
        regions.add(new Region("440304", "福田区", "福田", LocationType.DISTRICT, "440300"));
        regions.add(new Region("440304001000", "南园街道", "南园", LocationType.STREET, "440304"));
        regions.add(new Region("440304002000", "园岭街道", "园岭", LocationType.STREET, "440304"));
        regions.add(new Region("440304002017", "园东社区", "园东", LocationType.COMMUNITY, "440304002000"));
        return new Regions(regions);
    }
}
