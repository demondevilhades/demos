package com.hades.jeval.function.math;

import java.math.BigDecimal;

import org.nevec.rjm.BigDecimalMath;

import com.hades.jeval.function.FunctionConstants;

import net.sourceforge.jeval.Evaluator;
import net.sourceforge.jeval.function.Function;
import net.sourceforge.jeval.function.FunctionException;
import net.sourceforge.jeval.function.FunctionResult;

public class Sin implements Function {
    /**
     * Returns the name of the function - "sin".
     * 
     * @return The name of this function class.
     */
    public String getName() {
        return "sin";
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
     * @return The sine of the argument.
     * 
     * @exception FunctionException
     *                Thrown if the argument(s) are not valid for this function.
     */
    public FunctionResult execute(final Evaluator evaluator, final String arguments) throws FunctionException {
        BigDecimal result = null;

        try {
            result = BigDecimalMath.sin(new BigDecimal(arguments));
        } catch (Exception e) {
            throw new FunctionException("Invalid argument.", e);
        }
        return new FunctionResult(result.toString(), FunctionConstants.FUNCTION_RESULT_TYPE_BIGDECIMAL);
    }
}