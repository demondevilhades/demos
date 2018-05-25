package test.nlp.newword;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import test.nlp.segment.Segmentations;
import test.nlp.segment.Segmentations.SegType;

public class NewwordExtractionTest {

    private NewwordExtraction newwordExtraction;

    @Before
    public void before() {
        this.newwordExtraction = new NewwordExtraction.Builder(Segmentations.getSegmenter(SegType.NLP)
                .enableNameRecognize(false).enablePlaceRecognize(false)).maxLength(4).minFreq(2).entropyThreshold(1.5)
                .miThreshold(6).build();
    }

    @Test
    public void test() {
        newwordExtraction
                .add("深安监管〔2017〕132号各区（新区）安全监管局：现将《国家安全监管总局办公厅关于印发第八届中国国际安全生产及职业健康展览会优秀技术装备目录的通知》（安监总厅科技函〔2017〕57号）转发给你们，请结合实际，认真做好推广应用工作，充分发挥先进技术装备对防范和遏制重特大生产安全事故的作用，促进企业安全生产技防水平不断提升。附件：国家安全监管总局办公厅关于印发第八届中国国际安全生产及职业健康展览会优秀技术装备目录的通知（安监总厅科技函〔2017〕57号）深圳市安全生产监督管理局2017年5月2日");
        List<CandidateWord> candidateWords = newwordExtraction.extract();
        System.out.println(candidateWords);
    }
}
