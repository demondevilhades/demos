package com.hades.jeval;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.hades.jeval.function.Function;
import com.hades.jeval.operator.bd.AbstractBigDecimalOperator;

public final class BigDecimalEvaluatorFactory extends EvaluatorFactory {

    public static final BigDecimalEvaluatorFactory FACTORY = new BigDecimalEvaluatorFactory();

    final Map<String, AbstractBigDecimalOperator> bdOperatorMap = new HashMap<String, AbstractBigDecimalOperator>();

    final List<String> bdOperatorMapKeyList = new LinkedList<String>();

    private final Map<String, Function> functionMap = new HashMap<String, Function>();

    private BigDecimalEvaluatorFactory() {
        super();
    }

    public void addBigDecimalOperator(AbstractBigDecimalOperator operator) {
        bdOperatorMapKeyList.add(operator.getSymbol());
        bdOperatorMap.put(operator.getSymbol(), operator);
    }

    public void addFunctionMap(Function function) {
        functionMap.put(function.getName(), function);
    }

    AbstractBigDecimalOperator getAbstractBigDecimalOperator(String key) {
        return bdOperatorMap.get(key);
    }

    AbstractBigDecimalOperator getAbstractBigDecimalOperatorByStr(String str) {
        for (String key : bdOperatorMapKeyList) {
            if (str.startsWith(key)) {
                return bdOperatorMap.get(key);
            }
        }
        return null;
    }

    Function getFunction(String key) {
        return functionMap.get(key);
    }

    public Evaluator createEvaluator(String expression) {
        return new Evaluator(expression);
    }
}
