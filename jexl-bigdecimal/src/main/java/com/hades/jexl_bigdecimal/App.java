package com.hades.jexl_bigdecimal;

import java.math.BigDecimal;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;
import org.nevec.rjm.BigDecimalMath;

public class App {
    public static void main(String[] args) {
        JexlEngine engine = new JexlBuilder().create();
        JexlExpression expression = engine.createExpression("a * b");
        JexlContext context = new MapContext();
        context.set("a", "0.1");
        context.set("b", "3");
        System.out.println(expression.evaluate(context)); // 0.30000000000000004
        context.set("a", new BigDecimal("0.1"));
        context.set("b", "3");
        System.out.println(expression.evaluate(context)); // 0.3

        System.out.println(BigDecimalMath.exp(new BigDecimal("3.000").multiply(BigDecimalMath.log(new BigDecimal(
                "2.000"))))); // 7.996471
        JexlExpression expression2 = engine.createExpression("BigDecimalMath.exp(a * (BigDecimalMath.log( b )))");
        context.set("BigDecimalMath", BigDecimalMathObj.BIG_DECIMAL_MATH_OBJ);
        context.set("a", new BigDecimal("3.000"));
        context.set("b", new BigDecimal("2.000"));
        System.out.println(expression2.evaluate(context)); // 7.996471
    }
}
