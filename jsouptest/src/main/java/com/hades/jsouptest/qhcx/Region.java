package com.hades.jsouptest.qhcx;

public class Region {

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

    private LocationType locType;
    private String code;
    private String name;
    private String shortName;
    private String parentCode;

    public Region() {
    }

    public LocationType getLocType() {
        return locType;
    }

    public void setLocType(LocationType locType) {
        this.locType = locType;
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
}
