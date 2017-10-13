package com.hades.jeval.operator.operator;

import com.hades.jeval.operator.Operator;

public abstract class AbstractOperator implements Operator {
    protected String symbol = null;
    protected int precedence = 0;
    protected boolean unary = false;

    /**
     * A constructor that takes the operator symbol and precedence as input.
     * 
     * @param symbol
     *            The character(s) that makes up the operator.
     * @param precedence
     *            The precedence level given to this operator.
     */
    public AbstractOperator(final String symbol, final int precedence) {
        this.symbol = symbol;
        this.precedence = precedence;
    }

    /**
     * A constructor that takes the operator symbol, precedence, unary indicator
     * and unary precedence as input.
     * 
     * @param symbol
     *            The character(s) that makes up the operator.
     * @param precedence
     *            The precedence level given to this operator.
     * @param unary
     *            Indicates of the operator is a unary operator or not.
     */
    public AbstractOperator(String symbol, int precedence, boolean unary) {
        this.symbol = symbol;
        this.precedence = precedence;
        this.unary = unary;
    }

    /**
     * Returns the character(s) that makes up the operator.
     * 
     * @return The operator symbol.
     */
    @Override
    public String getSymbol() {
        return symbol;
    }

    /**
     * Returns the precedence given to this operator.
     * 
     * @return The precedecne given to this operator.
     */
    @Override
    public int getPrecedence() {
        return precedence;
    }

    /**
     * Returns the length of the operator symbol.
     * 
     * @return The length of the operator symbol.
     */
    @Override
    public int getLength() {
        return symbol.length();
    }

    /**
     * Returns an indicator of if the operator is unary or not.
     * 
     * @return An indicator of if the operator is unary or not.
     */
    @Override
    public boolean isUnary() {
        return unary;
    }

    /**
     * Determines if this operator is equal to another operator. Equality is
     * determined by comparing the operator symbol of both operators.
     * 
     * @param object
     *            The object to compare with this operator.
     * 
     * @return True if the object is equal and false if not.
     * 
     * @exception IllegalStateException
     *                Thrown if the input object is not of the Operator type.
     */
    @Override
    public boolean equals(final Object object) {
        if (object == null) {
            return false;
        }
        if (!(object instanceof AbstractOperator)) {
            throw new IllegalStateException("Invalid operator object.");
        }
        AbstractOperator operator = (AbstractOperator) object;
        if (symbol.equals(operator.getSymbol())) {
            return true;
        }
        return false;
    }

    /**
     * Returns the String representation of this operator, which is the symbol.
     * 
     * @return The operator symbol.
     */
    @Override
    public String toString() {
        return getSymbol();
    }
}