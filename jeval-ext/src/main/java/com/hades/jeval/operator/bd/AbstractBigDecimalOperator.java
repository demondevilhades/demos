package com.hades.jeval.operator.bd;

import java.math.BigDecimal;

import com.hades.jeval.operator.TypeOperator;

public interface AbstractBigDecimalOperator extends TypeOperator<BigDecimal> {

    /**
     * Evaluates two BigDecimal operands.
     * 
     * @param leftOperand
     *            The left operand being evaluated.
     * @param rightOperand
     *            The right operand being evaluated.
     * 
     * @return BigDecimal The value of the evaluated operands.
     * 
     * @exception EvaluateException
     *                Thrown when an error is found while evaluating the
     *                expression.
     */
    @Override
    public BigDecimal evaluate(final BigDecimal leftOperand, final BigDecimal rightOperand);

    /**
     * Evaluate one BigDecimal operand.
     * 
     * @param operand
     *            The operand being evaluated.
     */
    @Override
    public BigDecimal evaluate(final BigDecimal operand);
}