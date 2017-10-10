package com.hades.jeval.function.math;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.nevec.rjm.BigDecimalMath;

import com.hades.jeval.function.FunctionConstants;

import net.sourceforge.jeval.EvaluationConstants;
import net.sourceforge.jeval.Evaluator;
import net.sourceforge.jeval.function.Function;
import net.sourceforge.jeval.function.FunctionException;
import net.sourceforge.jeval.function.FunctionHelper;
import net.sourceforge.jeval.function.FunctionResult;

public class Pow implements Function {
    /**
     * Returns the name of the function - "pow".
     * 
     * @return The name of this function class.
     */
    public String getName() {
        return "pow";
    }

    /**
     * Executes the function for the specified argument. This method is called
     * internally by Evaluator.
     * 
     * @param evaluator
     *            An instance of Evaluator.
     * @param arguments
     *            A string argument that will be converted into two double
     *            values and evaluated.
     * 
     * @return The value of the first argument raised to the second power of the
     *         second argument.
     * 
     * @exception FunctionException
     *                Thrown if the argument(s) are not valid for this function.
     */
    public FunctionResult execute(final Evaluator evaluator, final String arguments) throws FunctionException {

        @SuppressWarnings("unchecked")
        ArrayList<String> numbers = FunctionHelper.getStrings(arguments,
                EvaluationConstants.FUNCTION_ARGUMENT_SEPARATOR);

        if (numbers.size() != 2) {
            throw new FunctionException("Two numeric arguments are required.");
        }
        BigDecimal result = null;

        try {
            result = BigDecimalMath.pow(new BigDecimal(numbers.get(0)), new BigDecimal(numbers.get(1)));
        } catch (Exception e) {
            throw new FunctionException("Two numeric arguments are required.", e);
        }
        return new FunctionResult(result.toString(), FunctionConstants.FUNCTION_RESULT_TYPE_BIGDECIMAL);
    }
}