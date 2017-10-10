package com.hades.jeval.function.math;

import net.sourceforge.jeval.Evaluator;
import net.sourceforge.jeval.function.Function;
import net.sourceforge.jeval.function.FunctionException;
import net.sourceforge.jeval.function.FunctionResult;

/**
 * not support
 */
@Deprecated
public class ToDegrees implements Function {
    public ToDegrees() {
        throw new UnsupportedOperationException();
    }

    public String getName() {
        throw new UnsupportedOperationException();
    }

    public FunctionResult execute(Evaluator evaluator, String arguments) throws FunctionException {
        throw new UnsupportedOperationException();
    }
}