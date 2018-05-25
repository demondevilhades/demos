package test.nlp.ltp;

/**
 * 主语或宾语类
 */
public class SubjectOrObject {

    /**
     * 定中关系做主语或宾语
     */
    private DPTerms dpTerms;

    /**
     * 主语或宾语同时也是一个主谓宾关系
     */
    private MainPart mainPart;

    public SubjectOrObject(DPTerms dpTerms) {
        this.dpTerms = dpTerms;
    }

    public SubjectOrObject(MainPart mainPart) {
        this.mainPart = mainPart;
    }

    public DPTerms getDpTerms() {
        return dpTerms;
    }

    public MainPart getMainPart() {
        return mainPart;
    }

    public int size() {
        return dpTerms == null ? mainPart.size() : dpTerms.size();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SubjectOrObject)) {
            return false;
        }

        SubjectOrObject other = (SubjectOrObject) obj;
        if (mainPart == null) {
            return this.dpTerms.equals(other.dpTerms);
        }
        return this.mainPart.equals(other.mainPart);
    }

    @Override
    public String toString() {
        return dpTerms == null ? mainPart.toString() : dpTerms.toString();
    }
}
