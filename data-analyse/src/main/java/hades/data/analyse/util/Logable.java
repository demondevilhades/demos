package hades.data.analyse.util;

import hades.data.analyse.data.DataArrayList;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public interface Logable {

    default void debugData(Logger logger, int index, BigDecimal bd) {
        if (logger.isDebugEnabled()) {
            logger.debug(new StringBuilder().append("data[").append(index).append("]=").append(bd).toString());
        }
    }

    default void debugData(Logger logger, String dataName, int index, BigDecimal bd) {
        if (StringUtils.isEmpty(dataName)) {
            debugData(logger, index, bd);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug(new StringBuilder().append(dataName).append("[").append(index).append("]=").append(bd)
                        .toString());
            }
        }
    }

    default void debugParam(Logger logger, Map<String, BigDecimal> paramMap, DataArrayList... dataArrayList) {
        if (logger.isDebugEnabled()) {
            logger.debug(new StringBuilder().append("paramMap=").append(paramMap).toString());
            if (dataArrayList != null) {
                for (int i = 0; i < dataArrayList.length; i++) {
                    logger.debug(new StringBuilder().append("dataArrayList[").append(i).append("]=")
                            .append(dataArrayList[i]).toString());
                }
            }
        }
    }
}
