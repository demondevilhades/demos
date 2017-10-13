package com.hades.jeval.operator;


public interface TypeOperator<T> extends Operator {

    /**
     * Evaluates two T operands.
     * 
     * @param leftOperand
     *            The left operand being evaluated.
     * @param rightOperand
     *            The right operand being evaluated.
     */
    public T evaluate(final T leftOperand, final T rightOperand);

    /**
     * Evaluate one T operand.
     * 
     * @param operand
     *            The operand being evaluated.
     */
    public T evaluate(final T operand);
}
