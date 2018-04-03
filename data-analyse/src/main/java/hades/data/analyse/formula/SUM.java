package hades.data.analyse.formula;

import hades.data.analyse.data.Data;
import hades.data.analyse.data.DataArrayList;
import hades.data.analyse.data.DataResult;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

/**
 * sum = Σ(xa)
 * 
 * @author hades
 */
public class SUM implements Formula {

    private final Logger logger = Logger.getLogger(this.getClass());

    private final int a;
    protected final String dataName;

    public SUM(int a) {
        this.a = a;
        this.dataName = null;
    }

    public SUM(int a, String dataName) {
        this.a = a;
        this.dataName = dataName;
    }

    public Data calcData(DataArrayList data, int index) {
        BigDecimal result = BigDecimal.ZERO;
        for (int i = index - a + 1; i <= index; i++) {
            result.add(data.get(i).getInputData());
        }
        debugData(logger, index, result);
        return new DataResult(result);
    }

    @Override
    public DataArrayList calc(DataArrayList data) {
        if (logger.isDebugEnabled()) {

        }
        if (a < 1 || a > data.size()) {
            return null;
        }
        DataArrayList result = new DataArrayList(data.size());
        for (int i = 0; i < a - 1; i++) {
            result.add(null);
            debugData(logger, i, null);
        }
        for (int i = a - 1; i < data.size(); i++) {
            result.add(calcData(data, i));
        }
        return result;
    }

    public int getA() {
        return a;
    }

    public String getDataName() {
        return dataName;
    }
}
