package com.hades.jeval.function.math;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.hades.jeval.function.FunctionConstants;

import net.sourceforge.jeval.EvaluationConstants;
import net.sourceforge.jeval.Evaluator;
import net.sourceforge.jeval.function.Function;
import net.sourceforge.jeval.function.FunctionException;
import net.sourceforge.jeval.function.FunctionHelper;
import net.sourceforge.jeval.function.FunctionResult;

public class Mod implements Function {

    @Override
    public String getName() {
        return "mod";
    }

    @Override
    public FunctionResult execute(Evaluator evaluator, String arguments) throws FunctionException {

        @SuppressWarnings("unchecked")
        ArrayList<String> numbers = FunctionHelper.getStrings(arguments,
                EvaluationConstants.FUNCTION_ARGUMENT_SEPARATOR);

        if (numbers.size() != 2) {
            throw new FunctionException("Two numeric arguments are required.");
        }
        BigDecimal result = null;

        try {
            result = new BigDecimal(numbers.get(0)).remainder(new BigDecimal(numbers.get(1)));
        } catch (Exception e) {
            throw new FunctionException("Two numeric arguments are required.", e);
        }
        return new FunctionResult(result.toString(), FunctionConstants.FUNCTION_RESULT_TYPE_BIGDECIMAL);
    }
}
