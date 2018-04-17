package com.hades.jsouptest.qhcx;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class RegionUtils {
    private static final Logger logger = Logger.getLogger(RegionUtils.class);

    private static RegionUtils utils = null;
    static {
        logger.info("load regionShortNameFilter");
        File file = new File(RegionUtils.class.getResource("/regionShortNameFilter.properties").getFile());
        FileInputStream fis = null;
        InputStreamReader isr = null;
        try {
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis, "UTF-8");
            Properties props = new Properties();
            props.load(isr);

            utils = new RegionUtils();
            utils.strs0 = props.getProperty("strs0").split(",");
            utils.strs1 = props.getProperty("strs1").split(",");
            utils.strs2 = props.getProperty("strs2").split(",");
            utils.strs3 = props.getProperty("strs3").split(",");
            utils.strs4 = props.getProperty("strs4").split(",");
            utils.mz = props.getProperty("mz").split(",");
            String[] mapStrs = props.getProperty("map").split(",");
            utils.shortNameMap = new HashMap<String, String>(mapStrs.length);
            for (String mapStr : mapStrs) {
                String[] split = mapStr.split(":");
                utils.shortNameMap.put(split[0], split[1]);
            }

        } catch (IOException e) {
            logger.error("", e);
        } finally {
            try {
                if (isr != null) {
                    isr.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                logger.error("", e);
            }
        }
    }

    public static RegionUtils getInstance() {
        return utils;
    }

    private String[] strs0;
    private String[] strs1;
    private String[] strs2;
    private String[] strs3;
    private String[] strs4;
    private String[] mz;
    private Map<String, String> shortNameMap;

    private RegionUtils() {
    }

    public void fixRegionShortName(Region region) {
        switch (region.getLocType()) {
        case PROVINCE:
            fixRegionShortName(region, strs0, mz);
            break;
        case CITY:
            fixRegionShortName(region, strs1, mz);
            break;
        case DISTRICT:
            fixRegionShortName(region, strs2, mz);
            break;
        case STREET:
            fixRegionShortName(region, strs3, null);
            break;
        case COMMUNITY:
            fixRegionShortName(region, strs4, null);
            break;
        default:
            break;
        }
    }

    private void fixRegionShortName(Region region, String[] strs, String[] mz) {
        String shortName = region.getName();
        for (String str : strs) {
            if (shortName.endsWith(str)) {
                shortName = shortName.replaceFirst(str, "");
                break;
            }
        }
        if (mz != null) {
            for (String str : mz) {
                shortName = shortName.replaceFirst(str, "");
            }
        }
        if (StringUtils.isNotEmpty(shortName)) {
            region.setShortName(shortName);
        }
    }

    public void fixRegionShortName(MultiShortNameRegion region) {
        switch (region.getLocType()) {
        case PROVINCE:
            fixRegionShortName(region, strs0, mz);
            break;
        case CITY:
            fixRegionShortName(region, strs1, mz);
            break;
        case DISTRICT:
            fixRegionShortName(region, strs2, mz);
            break;
        case STREET:
            fixRegionShortName(region, strs3, null);
            break;
        case COMMUNITY:
            fixRegionShortName(region, strs4, null);
            break;
        default:
            break;
        }
    }

    private void fixRegionShortName(MultiShortNameRegion region, String[] strs, String[] mz) {
        String name = region.getName();
        String shortName = region.getName();
        for (String str : strs) {
            if (shortName.endsWith(str)) {
                shortName = shortName.replaceFirst(str, "");
                break;
            }
        }
        if (mz != null) {
            for (String str : mz) {
                shortName = shortName.replaceFirst(str, "");
            }
        }
        if (StringUtils.isNotEmpty(shortName)) {
            List<String> shortNameList = new ArrayList<String>(2);
            shortNameList.add(shortName);
            for (Map.Entry<String, String> entry : shortNameMap.entrySet()) {
                if (name.endsWith(entry.getKey())) {
                    shortNameList.add(shortName + entry.getValue());
                }
            }
            region.setShortNameList(shortNameList);
        }
    }
}
