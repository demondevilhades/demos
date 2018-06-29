package com.hades.jexl_bigdecimal;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class SATest {

    @Test
    public void test() {
        @SuppressWarnings("serial")
        Map<String, Object> variableMap = new HashMap<String, Object>() {
            {
                put("Math", Math.class);
            }
        };
        SA sa = new SA("Math.pow(x, 5) - 5 * Math.pow(x, 3) + x + 10", variableMap, "x", 100, 1e-8, 100, 0.98,
                new double[] { -2, 2 });
        System.out.println(sa.run());
    }
}
