package com.hades.jeval.operator;

/**
 * An oerator than can specified in an expression.
 */
public interface Operator<T> {

    /**
     * Evaluates two T operands.
     * 
     * @param leftOperand
     *            The left operand being evaluated.
     * @param rightOperand
     *            The right operand being evaluated.
     */
    public abstract T evaluate(final T leftOperand, final T rightOperand);

    /**
     * Evaluate one T operand.
     * 
     * @param operand
     *            The operand being evaluated.
     */
    public abstract T evaluate(final T operand);

    /**
     * Returns the character(s) that makes up the operator.
     * 
     * @return The operator symbol.
     */
    public abstract String getSymbol();

    /**
     * Returns the precedence given to this operator.
     * 
     * @return The precedecne given to this operator.
     */
    public abstract int getPrecedence();

    /**
     * Returns the length of the operator symbol.
     * 
     * @return The length of the operator symbol.
     */
    public abstract int getLength();

    /**
     * Returns an indicator of if the operator is unary or not.
     * 
     * @return An indicator of if the operator is unary or not.
     */
    public abstract boolean isUnary();
}