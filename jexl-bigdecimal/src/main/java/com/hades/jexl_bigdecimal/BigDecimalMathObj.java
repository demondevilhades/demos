package com.hades.jexl_bigdecimal;

import java.math.BigDecimal;

import org.nevec.rjm.BigDecimalMath;

public class BigDecimalMathObj {

    public static final BigDecimalMathObj BIG_DECIMAL_MATH_OBJ = new BigDecimalMathObj();

    private BigDecimalMathObj() {
    }

    public BigDecimal log(BigDecimal x) {
        return BigDecimalMath.log(x);
    }

    public BigDecimal exp(BigDecimal x) {
        return BigDecimalMath.exp(x);
    }
}
