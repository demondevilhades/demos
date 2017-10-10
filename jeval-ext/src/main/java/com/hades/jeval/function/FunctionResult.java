package com.hades.jeval.function;

import net.sourceforge.jeval.function.FunctionException;

public class FunctionResult {

    private String result;
    private int type;

    /**
     * Constructor.
     * 
     * @param result
     *            The result value.
     * @param type
     *            The result type.
     * 
     * @throws FunctionException
     *             Thrown if result type is invalid.
     */
    public FunctionResult(String result, int type) throws FunctionException {

        if (type < FunctionConstants.FUNCTION_RESULT_TYPE_NUMERIC
                || type > FunctionConstants.FUNCTION_RESULT_TYPE_BIGDECIMAL) {

            throw new FunctionException("Invalid function result type.");
        }

        this.result = result;
        this.type = type;
    }

    /**
     * Returns the result value.
     * 
     * @return The result value.
     */
    public String getResult() {
        return result;
    }

    /**
     * Returns the result type.
     * 
     * @return The result type.
     */
    public int getType() {
        return type;
    }
}
