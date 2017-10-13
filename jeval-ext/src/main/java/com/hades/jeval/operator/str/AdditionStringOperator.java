package com.hades.jeval.operator.str;

import com.hades.jeval.operator.operator.AdditionOperator;

public class AdditionStringOperator extends AdditionOperator implements AbstractStringOperator {

    @Override
    public String evaluate(final String leftOperand, final String rightOperand) {
        return leftOperand + rightOperand;
    }

    @Override
    public String evaluate(final String operand) {
        return operand;
    }
}