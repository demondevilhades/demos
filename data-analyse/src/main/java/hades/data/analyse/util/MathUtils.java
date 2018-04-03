package hades.data.analyse.util;

import hades.data.analyse.data.DataArrayList;
import hades.data.analyse.data.DataResult;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.apache.log4j.Logger;

public class MathUtils {
    public static final int DEF_SCALE = 4;
    public static final RoundingMode DEF_RM = RoundingMode.HALF_UP;
    public static final MathContext DEF_MC = new MathContext(DEF_SCALE, DEF_RM);

    public static final DIFF DIFF = new DIFF();

    public static class DIFF implements Logable {

        DIFF() {
        }

        public DataArrayList calc(DataArrayList data0, DataArrayList data1, Logger logger, String dataName) {
            if (data0.size() != data1.size()) {
                return null;
            }
            DataArrayList diff = new DataArrayList(data0.size());
            for (int i = 0; i < data0.size(); i++) {
                BigDecimal bd = data0.get(i).getInputData().subtract(data1.get(i).getInputData());
                diff.add(new DataResult(bd));
                debugData(logger, dataName, i, bd);
            }
            return diff;
        }
    }
}
