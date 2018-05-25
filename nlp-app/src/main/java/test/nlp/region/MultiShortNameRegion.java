package test.nlp.region;

import java.io.Serializable;
import java.util.List;

/**
 * 区划信息
 * 
 * @author zs
 */
public class MultiShortNameRegion extends Region implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> shortNameList;

    public MultiShortNameRegion() {
    }

    public MultiShortNameRegion(String code, String name, List<String> shortNameList, LocationType locType) {
        super(code, name, null, locType);
        this.shortNameList = shortNameList;
    }

    public MultiShortNameRegion(String code, String name, List<String> shortNameList, LocationType locType,
            String parentCode) {
        super(code, name, null, locType, parentCode);
        this.shortNameList = shortNameList;
    }

    public List<String> getShortNameList() {
        return shortNameList;
    }

    public void setShortNameList(List<String> shortNameList) {
        this.shortNameList = shortNameList;
    }
}
