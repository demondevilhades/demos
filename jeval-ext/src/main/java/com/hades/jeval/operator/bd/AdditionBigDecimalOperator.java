package com.hades.jeval.operator.bd;

import java.math.BigDecimal;

import com.hades.jeval.operator.operator.AdditionOperator;

public class AdditionBigDecimalOperator extends AdditionOperator implements AbstractBigDecimalOperator {

    @Override
    public BigDecimal evaluate(final BigDecimal leftOperand, final BigDecimal rightOperand) {
        return leftOperand.add(rightOperand);
    }

    @Override
    public BigDecimal evaluate(final BigDecimal operand) {
        return operand;
    }
}