package com.hades.jeval;

public class Evaluator extends net.sourceforge.jeval.Evaluator {

    public Evaluator() {
        super();
    }

    public Evaluator(char quoteCharacter, boolean loadMathVariables, boolean loadMathFunctions,
            boolean loadStringFunctions, boolean processNestedFunctions) {
        super(quoteCharacter, loadMathVariables, loadMathFunctions, loadStringFunctions, processNestedFunctions);
    }
}
