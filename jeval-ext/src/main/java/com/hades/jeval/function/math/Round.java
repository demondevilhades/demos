package com.hades.jeval.function.math;

import net.sourceforge.jeval.Evaluator;
import net.sourceforge.jeval.function.Function;
import net.sourceforge.jeval.function.FunctionException;
import net.sourceforge.jeval.function.FunctionResult;

/**
 * not support
 */
@Deprecated
public class Round implements Function {
    public Round() {
        throw new UnsupportedOperationException();
    }

    public String getName() {
        throw new UnsupportedOperationException();
    }

    public FunctionResult execute(final Evaluator evaluator, final String arguments) throws FunctionException {
        throw new UnsupportedOperationException();
    }
}