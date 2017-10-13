package com.hades.jeval;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.hades.jeval.operator.Operator;
import com.hades.jeval.operator.util.Constants;

public class BigDecimalEvaluator {
    private final String expression;

    private final List<String> varList = new LinkedList<String>();

    private BigDecimalEvaluator leftBDE = null;
    private BigDecimalEvaluator rightBDE = null;

    public BigDecimalEvaluator(String expression) {
        this.expression = expression;
        loadExpression();
    }

    private void loadExpression() {
        varList.clear();

        final char[] charArray = expression.toCharArray();
        int charLen = charArray.length;
        int charLeft = 0;
        int charRight;

        char ch;
        Operator operator;
        int operatorIndex;
        while (charLeft < charLen) {
            ch = charArray[charLeft];
            operator = null;
            operatorIndex = -1;

            // Skip any white space.
            if (Character.isWhitespace(ch)) {
                charLeft++;
                continue;
            }
            // open parentheses
            else if (Constants.OPEN_PARENTHESES == ch) {
                charRight = findCloseParentheses(charLeft, charLen, charArray);
                leftBDE = new BigDecimalEvaluator(new String(Arrays.copyOfRange(charArray, charLeft + 1, charRight)));
                if (charRight + 1 < charLen) {
                    rightBDE = new BigDecimalEvaluator(
                            new String(Arrays.copyOfRange(charArray, charRight + 1, charLen)));
                }
                break;
            }
            // str
            else if (Constants.SINGLE_QUOTE == ch) {

            }
            // var
            else if (Constants.POUND_SIGN == ch) {

            }
            // function or op
            else if (Character.isLetter(ch)) {
                // is function

                // is bd op

                // is op
            } else {
                throw new EvaluationException("cannot parse : " + expression);
            }
        }
        // TODO
    }

    private int findCloseParentheses(int charLeft, int charLen, char[] charArray) {
        int charRight = charLeft + 1;
        int layer = 1;
        while (charRight < charLen && layer != 0) {
            if (Constants.OPEN_PARENTHESES == charArray[charRight]) {
                layer++;
            } else if (Constants.CLOSED_PARENTHESES == charArray[charRight]) {
                layer--;
            }
            charRight++;
        }
        if (layer != 0) {
            throw new EvaluationException("parentheses error : " + expression);
        }
        return charRight;
    }

    public String evaluate() {
        // TODO
        return null;
    }
}
