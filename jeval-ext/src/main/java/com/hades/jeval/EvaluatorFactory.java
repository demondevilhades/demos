package com.hades.jeval;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.hades.jeval.function.Function;
import com.hades.jeval.operator.bd.AbstractBigDecimalOperator;
import com.hades.jeval.operator.bd.AdditionBigDecimalOperator;
import com.hades.jeval.operator.d.AbstractDoubleOperator;
import com.hades.jeval.operator.d.AdditionDoubleOperator;
import com.hades.jeval.operator.operator.AbstractOperator;
import com.hades.jeval.operator.operator.ClosedParenthesesOperator;
import com.hades.jeval.operator.operator.OpenParenthesesOperator;
import com.hades.jeval.operator.str.AbstractStringOperator;
import com.hades.jeval.operator.str.AdditionStringOperator;

/**
 * TODO
 * 
 * @author HaDeS
 */
public abstract class EvaluatorFactory {

    // public static final EvaluatorFactory FACTORY = new EvaluatorFactory();

    private final OpenParenthesesOperator openParenthesesOperator = new OpenParenthesesOperator();
    private final ClosedParenthesesOperator closedParenthesesOperator = new ClosedParenthesesOperator();

    private final Map<String, AbstractOperator> operatorMap = new HashMap<String, AbstractOperator>();
    private final Map<String, AbstractBigDecimalOperator> bdOperatorMap = new HashMap<String, AbstractBigDecimalOperator>();
    private final Map<String, AbstractDoubleOperator> dOperatorMap = new HashMap<String, AbstractDoubleOperator>();
    private final Map<String, AbstractStringOperator> strOperatorMap = new HashMap<String, AbstractStringOperator>();

    private final List<String> operatorMapKeyList = new LinkedList<String>();
    private final List<String> bdOperatorMapKeyList = new LinkedList<String>();
    private final List<String> dOperatorMapKeyList = new LinkedList<String>();
    private final List<String> strOperatorMapKeyList = new LinkedList<String>();

    private final Map<String, Function> functionMap = new HashMap<String, Function>();

    protected EvaluatorFactory() {
        initOperators();
        initFunctions();
    }

    protected void initOperators() {
        addOperator(openParenthesesOperator);
        addOperator(closedParenthesesOperator);

        addBigDecimalOperator(new AdditionBigDecimalOperator());
        addDoubleOperator(new AdditionDoubleOperator());
        addStringOperator(new AdditionStringOperator());
        // TODO
    }

    protected void initFunctions() {
        // TODO
    }

    public void addOperator(AbstractOperator operator) {
        operatorMapKeyList.add(operator.getSymbol());
        operatorMap.put(operator.getSymbol(), operator);
    }

    public void addBigDecimalOperator(AbstractBigDecimalOperator operator) {
        bdOperatorMapKeyList.add(operator.getSymbol());
        bdOperatorMap.put(operator.getSymbol(), operator);
    }

    public void addDoubleOperator(AbstractDoubleOperator operator) {
        dOperatorMapKeyList.add(operator.getSymbol());
        dOperatorMap.put(operator.getSymbol(), operator);
    }

    public void addStringOperator(AbstractStringOperator operator) {
        strOperatorMapKeyList.add(operator.getSymbol());
        strOperatorMap.put(operator.getSymbol(), operator);
    }

    public void addFunctionMap(Function function) {
        functionMap.put(function.getName(), function);
    }

    AbstractOperator getOperator(String key) {
        return operatorMap.get(key);
    }

    AbstractBigDecimalOperator getAbstractBigDecimalOperator(String key) {
        return bdOperatorMap.get(key);
    }

    AbstractDoubleOperator getAbstractDoubleOperator(String key) {
        return dOperatorMap.get(key);
    }

    AbstractStringOperator getAbstractStringOperator(String key) {
        return strOperatorMap.get(key);
    }

    AbstractOperator getOperatorByStr(String str) {
        for (String key : operatorMapKeyList) {
            if (str.startsWith(key)) {
                return operatorMap.get(key);
            }
        }
        return null;
    }

    AbstractBigDecimalOperator getAbstractBigDecimalOperatorByStr(String str) {
        for (String key : bdOperatorMapKeyList) {
            if (str.startsWith(key)) {
                return bdOperatorMap.get(key);
            }
        }
        return null;
    }

    AbstractDoubleOperator getAbstractDoubleOperatorByStr(String str) {
        for (String key : dOperatorMapKeyList) {
            if (str.startsWith(key)) {
                return dOperatorMap.get(key);
            }
        }
        return null;
    }

    AbstractStringOperator getAbstractStringOperatorByStr(String str) {
        for (String key : strOperatorMapKeyList) {
            if (str.startsWith(key)) {
                return strOperatorMap.get(key);
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
