package com.hades.jeval.function.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

import net.sourceforge.jeval.Evaluator;
import net.sourceforge.jeval.function.Function;
import net.sourceforge.jeval.function.FunctionException;
import net.sourceforge.jeval.function.FunctionResult;

import com.hades.jeval.function.FunctionConstants;

public class Rint implements Function {

    /**
     * Returns the name of the function - "rint".
     * 
     * @return The name of this function class.
     */
    public String getName() {
        return "rint";
    }

    /**
     * Executes the function for the specified argument. This method is called
     * internally by Evaluator.
     * 
     * @param evaluator
     *            An instance of Evaluator.
     * @param arguments
     *            A string argument that will be converted to a double value and
     *            evaluated.
     * 
     * @return A double value that is closest in value to the argument and is
     *         equal to a mathematical integer.
     * 
     * @exception FunctionException
     *                Thrown if the argument(s) are not valid for this function.
     */
    public FunctionResult execute(final Evaluator evaluator, final String arguments) throws FunctionException {
        BigDecimal result = null;

        try {
            result = new BigDecimal(arguments).setScale(0, RoundingMode.HALF_EVEN);
        } catch (Exception e) {
            throw new FunctionException("Invalid argument.", e);
        }
        return new FunctionResult(result.toString(), FunctionConstants.FUNCTION_RESULT_TYPE_BIGDECIMAL);
    }
}