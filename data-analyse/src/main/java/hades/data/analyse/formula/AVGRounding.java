package hades.data.analyse.formula;

import hades.data.analyse.data.Data;
import hades.data.analyse.data.DataArrayList;
import hades.data.analyse.data.DataResult;
import hades.data.analyse.util.MathUtils;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

public class AVGRounding extends AVG implements Formula {

    private final Logger logger = Logger.getLogger(this.getClass());

    public AVGRounding(int a) {
        super(a);
    }

    public AVGRounding(int a, String dataName) {
        super(a, dataName);
    }

    @Override
    public Data calcData(DataArrayList data, int index) {
        BigDecimal result = BigDecimal.ZERO;
        for (int i = index - a + 1; i <= index; i++) {
            result.add(data.get(i).getInputData());
        }
        result = result.divide(new BigDecimal(a), MathUtils.DEF_MC).setScale(MathUtils.DEF_SCALE, MathUtils.DEF_RM);
        debugData(logger, index, result);
        return new DataResult(result);
    }
}
