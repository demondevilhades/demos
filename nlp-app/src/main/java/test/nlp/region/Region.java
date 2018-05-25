package test.nlp.region;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.base.Objects;

/**
 * 区划信息
 */
public class Region implements Serializable {

    public static enum LocationType {
        /**
         * 国家
         */
        COUNTRY,

        /**
         * 省、自治区、特别行政区
         */
        PROVINCE,

        /**
         * 市，自治州
         */
        CITY,

        /**
         * 区县
         */
        DISTRICT,

        /**
         * 街道
         */
        STREET,

        /**
         * 社区
         */
        COMMUNITY;
    }

    private static final long serialVersionUID = 1L;

    /**
     * 区划类型
     */
    private LocationType locType;

    /**
     * 区划编号
     */
    private String code;

    /**
     * 区划名称
     */
    private String name;

    private String shortName;

    /**
     * 上级区划编号
     */
    private String parentCode;

    private transient Region parentRegion;

    public Region() {

    }

    public Region(String code, String name, String shortName, LocationType locType) {
        this.code = code;
        this.name = name;
        this.shortName = shortName;
        this.locType = locType;
    }

    public Region(String code, String name, String shortName, LocationType locType, String parentCode) {
        this(code, name, shortName, locType);
        this.parentCode = parentCode;
    }

    public LocationType getLocType() {
        return locType;
    }

    public void setLocType(LocationType locType) {
        this.locType = locType;
    }

    public void setType(String locType)

    {
        this.locType = LocationType.valueOf(locType);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public Region getParentRegion() {
        return parentRegion;
    }

    public void setParentRegion(Region parentRegion) {
        this.parentRegion = parentRegion;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * 判断该区划是否属于另一个区划
     * 
     * @param other
     * @return
     */
    public boolean isBelong(Region other) {
        if (this.getLocType().ordinal() <= other.getLocType().ordinal()) {
            return false;
        }

        Region region = this;
        while (region.getLocType().ordinal() > other.getLocType().ordinal()) {
            region = region.getParentRegion();
        }
        return region.getCode().equals(other.code);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Region) {
            return Objects.equal(this.code, ((Region) obj).code);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
