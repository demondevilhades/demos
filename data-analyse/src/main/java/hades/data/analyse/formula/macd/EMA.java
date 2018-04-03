package hades.data.analyse.formula.macd;

import hades.data.analyse.data.Data;
import hades.data.analyse.data.DataArrayList;
import hades.data.analyse.data.DataResult;
import hades.data.analyse.formula.Formula;
import hades.data.analyse.util.MathUtils;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

/**
 * EXPMA <br>
 * ema[i] = x * 2 / ( a + 1 ) + ema[i - 1] * ( a - 1 ) / ( a + 1 )
 * 
 * @author hades
 */
public abstract class EMA implements Formula {

    private final Logger logger = Logger.getLogger(this.getClass());

    protected final BigDecimal a;
    protected final BigDecimal eBD;
    protected final String dataName;

    public EMA(BigDecimal a) {
        this.a = a;
        this.eBD = new BigDecimal(2).divide(a.add(BigDecimal.ONE), MathUtils.DEF_MC);
        this.dataName = null;
    }

    public EMA(BigDecimal a, String dataName) {
        this.a = a;
        this.eBD = new BigDecimal(2).divide(a.add(BigDecimal.ONE), MathUtils.DEF_MC);
        this.dataName = dataName;
    }

    public Data calcData(DataArrayList data, int index, DataArrayList result) {
        BigDecimal resultInputData = data.get(index).getInputData().multiply(eBD)
                .add(result.get(index - 1).getInputData().multiply(BigDecimal.ONE.subtract(eBD)));
        debugData(logger, dataName, index, resultInputData);
        return new DataResult(resultInputData);
    }

    @Override
    public DataArrayList calc(DataArrayList data) {
        DataArrayList result = new DataArrayList(data.size());
        result.add(new DataResult(data.get(0).getInputData()));
        debugData(logger, dataName, 0, result.get(0).getInputData());
        for (int i = 1; i < data.size(); i++) {
            result.add(calcData(data, i, result));
        }
        return result;
    }

    public BigDecimal getA() {
        return a;
    }

    public String getDataName() {
        return dataName;
    }
}
