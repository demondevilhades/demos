package com.hades.jeval.function.math;

import java.math.BigDecimal;

import org.nevec.rjm.BigDecimalMath;

import com.hades.jeval.function.FunctionConstants;

import net.sourceforge.jeval.Evaluator;
import net.sourceforge.jeval.function.Function;
import net.sourceforge.jeval.function.FunctionException;
import net.sourceforge.jeval.function.FunctionResult;

public class Tan implements Function {
    /**
     * Returns the name of the function - "tan".
     * 
     * @return The name of this function class.
     */
    public String getName() {
        return "tan";
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
     * @return A trigonometric tangent of an angle.
     * 
     * @exception FunctionException
     *                Thrown if the argument(s) are not valid for this function.
     */
    public FunctionResult execute(Evaluator evaluator, String arguments) throws FunctionException {
        BigDecimal result = null;

        try {
            result = BigDecimalMath.tan(new BigDecimal(arguments));
        } catch (Exception e) {
            throw new FunctionException("Invalid argument.", e);
        }
        return new FunctionResult(result.toString(), FunctionConstants.FUNCTION_RESULT_TYPE_BIGDECIMAL);
    }
}