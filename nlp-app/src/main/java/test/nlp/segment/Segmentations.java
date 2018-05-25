package test.nlp.segment;

public class Segmentations {

    /**
     * 分词器类型枚举类
     */
    public static enum SegType {
        /**
         * 索引
         */
        INDEX,
        /**
         * 检索
         */
        SEARCH,
        /**
         * NLP
         */
        NLP,
        /**
         * 单字
         */
        SINGLE,
        /**
         * 医疗
         */
        MED,
    }

    public static IWordSegmenter getSegmenter(SegType type) {
        return getSegmenter(type, null);
    }

    public static IWordSegmenter getSegmenter(SegType type, String domain) {
        IWordSegmenter wordSegmenter = null;
        switch (type) {
        case INDEX:
            wordSegmenter = new IndexWordSegmenter(domain);
            break;
        case SEARCH:
            wordSegmenter = new ViterbiWordSegmenter(domain).enableNumberRecognize(false);
            break;
        case NLP:
            wordSegmenter = new ViterbiWordSegmenter(domain).enablePOSTagger(true);
            break;
        case SINGLE:
            wordSegmenter = new SingleWordSegmenter(domain);
            break;
        case MED:
            wordSegmenter = new MedWordSegmenter(domain);
            break;
        default:
            throw new IllegalArgumentException("Invalid seg type [" + type.toString() + "]");
        }
        return wordSegmenter;
    }
}
