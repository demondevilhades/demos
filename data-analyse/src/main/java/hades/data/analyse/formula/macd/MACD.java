package hades.data.analyse.formula.macd;

import hades.data.analyse.data.DataArrayList;
import hades.data.analyse.formula.Formula;
import hades.data.analyse.util.MathUtils;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

/**
 * DIFF = EMA(short) - EMA(lang) <br>
 * DEA = EMA(DIFF, mid) <br>
 * MACD = DIFF - DEA
 * 
 * @author hades
 */
public class MACD implements Formula {

    private final Logger logger = Logger.getLogger(this.getClass());

    protected final EMA mid;
    protected final EMA emaShort;
    protected final EMA emaLong;

    public MACD(BigDecimal mid, BigDecimal emaShort, BigDecimal emaLong) {
        this.mid = new EMARounding(mid, "DEA");
        this.emaShort = new EMARounding(emaShort, "SHORT");
        this.emaLong = new EMARounding(emaLong, "LANG");
    }

    @Override
    public DataArrayList calc(DataArrayList data) {
        DataArrayList emaShortData = emaShort.calc(data);
        DataArrayList emaLongData = emaLong.calc(data);

        DataArrayList diffData = MathUtils.DIFF.calc(emaShortData, emaLongData, logger, "DIFF");

        DataArrayList deaData = mid.calc(diffData);

        DataArrayList macdData = MathUtils.DIFF.calc(diffData, deaData, logger, "MACD");

        return macdData;
    }
}
