package com.hades.jeval;

import java.util.Stack;

import net.sourceforge.jeval.EvaluationHelper;

import com.hades.jeval.operator.Operator;
import com.hades.jeval.operator.util.Constants;

public class Evaluator {
    private final String expression;

    private final Stack operandStack = new Stack();
    private final Stack operatorStack = new Stack();
    private char quoteCharacter = Constants.SINGLE_QUOTE;

    public Evaluator(String expression) {
        this.expression = expression;
        loadExpression();
    }

    private void loadExpression() {
        boolean haveOperand = false;
        boolean haveOperator = false;
        Operator unaryOperator = null;

        final char[] charArray = expression.toCharArray();
        int numChars = charArray.length;
        int charCtr = 0;

        Operator operator;
        while (charCtr < numChars) {
            if (EvaluationHelper.isSpace(expression.charAt(charCtr))) {
                charCtr++;
                continue;
            }

            // get Operator

            {
                boolean numQuoteCharacters = false;
                for (int charCtrIndex = charCtr; charCtr < numChars; charCtrIndex++) {
                    if (charArray[charCtrIndex] == quoteCharacter) {
                        numQuoteCharacters = !numQuoteCharacters;
                    }
                    if (numQuoteCharacters) {
                        continue;
                    }

                    // TODO
                }

            }

        }
        // TODO
    }

    public String evaluate() {
        // TODO
        return null;
    }
}
