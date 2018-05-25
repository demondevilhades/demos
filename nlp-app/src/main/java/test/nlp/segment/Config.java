package test.nlp.segment;

/**
 * 分词的一些配置信息
 */
public class Config {

    /**
     * 是否启用人名识别
     */
    private boolean enableNameRecognize;

    /**
     * 是否启用地名识别
     */
    private boolean enablePlaceRecognize;

    /**
     * 是否启用数词识别
     */
    private boolean enableNumberRecognize = true;

    /**
     * 是否启用词性标注
     */
    private boolean enablePOSTagger = true;

    public boolean isEnableNER() {
        return enableNameRecognize | enablePlaceRecognize;
    }

    public boolean isEnableNameRecognize() {
        return enableNameRecognize;
    }

    public void enableNameRecognize(boolean enableNameRecognize) {
        this.enableNameRecognize = enableNameRecognize;

    }

    public boolean isEnablePlaceRecognize() {
        return enablePlaceRecognize;
    }

    public void enablePlaceRecognize(boolean enablePlaceRecognize) {
        this.enablePlaceRecognize = enablePlaceRecognize;

    }

    public void enableNumberRecognize(boolean enableNumberRecognize) {
        this.enableNumberRecognize = enableNumberRecognize;
    }

    public boolean isEnableNumberRecognize() {
        return enableNumberRecognize;
    }

    public void enablePOSTagger(boolean enablePOSTagger) {
        this.enablePOSTagger = enablePOSTagger;
    }

    public boolean isEnablePOSTagger() {
        return enablePOSTagger;
    }

}
