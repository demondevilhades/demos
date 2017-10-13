package com.hades.jeval.operator.str;

import com.hades.jeval.operator.TypeOperator;

public interface AbstractStringOperator extends TypeOperator<String> {

    /**
     * Evaluates two string operands.
     * 
     * @param leftOperand
     *            The left operand being evaluated.
     * @param rightOperand
     *            The right operand being evaluated.
     * 
     * @return String The value of the evaluated operands.
     * 
     * @exception EvaluateException
     *                Thrown when an error is found while evaluating the
     *                expression.
     */
    @Override
    public String evaluate(final String leftOperand, final String rightOperand);

    /**
     * Evaluate one String operand.
     * 
     * @param operand
     *            The operand being evaluated.
     */
    @Override
    public String evaluate(final String operand);
}