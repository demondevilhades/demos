package hades.data.analyse.formula.macd;

import hades.data.analyse.data.Data;
import hades.data.analyse.data.DataArrayList;
import hades.data.analyse.data.DataResult;
import hades.data.analyse.formula.Formula;
import hades.data.analyse.util.MathUtils;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

public class EMARounding extends EMA implements Formula {

    private final Logger logger = Logger.getLogger(this.getClass());

    public EMARounding(BigDecimal a) {
        super(a);
    }

    public EMARounding(BigDecimal a, String dataName) {
        super(a, dataName);
    }

    @Override
    public Data calcData(DataArrayList data, int index, DataArrayList result) {
        BigDecimal resultInputData = data
                .get(index)
                .getInputData()
                .multiply(eBD)
                .add(result.get(index - 1).getInputData().multiply(BigDecimal.ONE.subtract(eBD))
                        .setScale(MathUtils.DEF_SCALE, MathUtils.DEF_RM));
        debugData(logger, dataName, index, resultInputData);
        return new DataResult(resultInputData);
    }
}
