package com.hades.jeval.operator;

public interface Operator {

    /**
     * Returns the character(s) that makes up the operator.
     * 
     * @return The operator symbol.
     */
    public String getSymbol();

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
    public int getLength();

    /**
     * Returns an indicator of if the operator is unary or not.
     * 
     * @return An indicator of if the operator is unary or not.
     */
    public boolean isUnary();
}
