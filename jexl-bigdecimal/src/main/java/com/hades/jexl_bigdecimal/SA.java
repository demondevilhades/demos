package com.hades.jexl_bigdecimal;

import java.util.Map;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;

/**
 * 模拟退火
 */
public class SA {

    private final String expression;
    private final Map<String, Object> variableMap;
    private final String paramStr;

    private int t_start = 100;// 初始化温度
    private double t_min = 1e-8;// 温度的下界
    private int k = 100;// 迭代的次数
    private double delta = 0.98;// 温度的下降率
    private double[] range;

    public SA(String expression, Map<String, Object> variableMap, String paramStr) {
        this.expression = expression;
        this.variableMap = variableMap;
        this.paramStr = paramStr;
    }

    public SA(String expression, Map<String, Object> variableMap, String paramStr, int t_start, double t_min, int k,
            double delta, double[] range) {
        this.expression = expression;
        this.variableMap = variableMap;
        this.paramStr = paramStr;
        this.t_start = t_start;
        this.t_min = t_min;
        this.k = k;
        this.delta = delta;
        this.range = range;
    }

    public double run() {
        JexlEngine engine = new JexlBuilder().create();
        JexlExpression jexlExpression = engine.createExpression(expression);
        JexlContext context = new MapContext();
        for (Map.Entry<String, Object> entry : variableMap.entrySet()) {
            context.set(entry.getKey(), entry.getValue());
        }

        double t = t_start;
        double[] x = new double[k];
        // 初始化初始解
        for (int i = 0; i < k; i++) {
            x[i] = Math.random() * k;
        }

        while (t > t_min) {
            for (int i = 0; i < k; i++) {
                context.set(paramStr, x[i]);
                double d = (double) jexlExpression.evaluate(context);
                // 在邻域内产生新的解
                double x_new = x[i] + (Math.random() * 2 - 1) * t;
                // 判断新的x不能超出界
                if (x_new >= range[0] && x_new <= range[1]) {
                    context.set(paramStr, x_new);
                    double d_new = (double) jexlExpression.evaluate(context);
                    if (d_new - d < 0) {
                        // 替换
                        x[i] = x_new;
                    } else {
                        // 概率替换
                        x[i] = (Math.random() < 1 / (1 + Math.exp(-(d_new - d) / t_start))) ? x_new : x[i];
                    }
                }
            }
            t *= delta;
        }

        double result = Double.POSITIVE_INFINITY;// 初始化最终的结果
        for (int i = 0; i < k; i++) {
            context.set(paramStr, x[i]);
            result = Math.min(result, (double) jexlExpression.evaluate(context));
        }
        return result;
    }

    public int getT_start() {
        return t_start;
    }

    public void setT_start(int t_start) {
        this.t_start = t_start;
    }

    public double getT_min() {
        return t_min;
    }

    public void setT_min(double t_min) {
        this.t_min = t_min;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public String getExpression() {
        return expression;
    }

    public Map<String, Object> getVariableMap() {
        return variableMap;
    }

    public double[] getRange() {
        return range;
    }

    public void setRange(double[] range) {
        this.range = range;
    }
}
