package com.hades.jeval;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class EvaluatorTest {

    @Test
    public void test() throws Exception {
        String expression = "pow(#{a},#{b}) + 1 * 2";
        Map<String, String> variables = new HashMap<String, String>();
        variables.put("a", "2");
        variables.put("b", "3");

        Evaluator evaluator = new Evaluator();
        evaluator.parse(expression);
        evaluator.setVariables(variables);
        System.out.println(evaluator.evaluate());
    }
}
