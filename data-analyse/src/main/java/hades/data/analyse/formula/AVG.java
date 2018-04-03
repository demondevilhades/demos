package hades.data.analyse.formula;

import hades.data.analyse.data.Data;
import hades.data.analyse.data.DataArrayList;
import hades.data.analyse.data.DataResult;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

/**
 * avg = Σ(x(a)) / a
 * 
 * @author hades
 */
public class AVG implements Formula {

    private final Logger logger = Logger.getLogger(this.getClass());

    protected final int a;
    protected final String dataName;

    public AVG(int a) {
        this.a = a;
        this.dataName = null;
    }

    public AVG(int a, String dataName) {
        this.a = a;
        this.dataName = dataName;
    }

    public Data calcData(DataArrayList data, int index) {
        BigDecimal result = BigDecimal.ZERO;
        for (int i = index - a + 1; i <= index; i++) {
            result.add(data.get(i).getInputData());
        }
        result = result.divide(new BigDecimal(a));
        debugData(logger, index, result);
        return new DataResult(result);
    }

    @Override
    public DataArrayList calc(DataArrayList data) {
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
