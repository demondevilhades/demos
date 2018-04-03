package hades.data.analyse.data;

import java.math.BigDecimal;

public class DataResult implements Data {

    private BigDecimal inputData;

    public DataResult() {
    }

    public DataResult(BigDecimal inputData) {
        this.inputData = inputData;
    }

    @Override
    public BigDecimal getInputData() {
        return inputData;
    }

    @Override
    public void setInputData(BigDecimal inputData) {
        this.inputData = inputData;
    }

    @Override
    public String toString() {
        return "DataResult [inputData=" + inputData + "]";
    }
}
