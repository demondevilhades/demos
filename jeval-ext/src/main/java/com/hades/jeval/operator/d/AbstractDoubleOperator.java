package com.hades.jeval.operator.d;

import com.hades.jeval.operator.TypeOperator;

public interface AbstractDoubleOperator extends TypeOperator<Double> {

    /**
     * Evaluates two Double operands.
     * 
     * @param leftOperand
     *            The left operand being evaluated.
     * @param rightOperand
     *            The right operand being evaluated.
     * 
     * @return Double The value of the evaluated operands.
     * 
     * @exception EvaluateException
     *                Thrown when an error is found while evaluating the
     *                expression.
     */
    @Override
    public Double evaluate(final Double leftOperand, final Double rightOperand);

    /**
     * Evaluate one Double operand.
     * 
     * @param operand
     *            The operand being evaluated.
     */
    @Override
    public Double evaluate(final Double operand);
}