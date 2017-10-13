package com.hades.jeval.operator.d;

import com.hades.jeval.operator.operator.AdditionOperator;

public class AdditionDoubleOperator extends AdditionOperator implements AbstractDoubleOperator {

    @Override
    public Double evaluate(final Double leftOperand, final Double rightOperand) {
        return leftOperand + rightOperand;
    }

    @Override
    public Double evaluate(Double operand) {
        return operand;
    }
}