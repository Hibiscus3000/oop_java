package main.java.ru.nsu.fit.oop.calculator.operations;

import main.java.ru.nsu.fit.oop.calculator.exception.NoDefinedParamWithGivenName;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Context {
    private Stack<Double> args = new Stack<>();
    private Map<String,Double> params = new HashMap<>();
    private int numberOfValuesInStack = 0;

    public void pushToStack(double value) {
        args.push(value);
        ++numberOfValuesInStack;
    }
    public double popFromStack() {
        --numberOfValuesInStack;
        return args.pop();
    }
    public double getFromStack() {
        return args.peek();
    }
    public void defineNamedParam(String key, double value)
    {
        params.put(key,value);
    }
    public void pushNamedParamToStack(String key) throws NoDefinedParamWithGivenName {
        if (!params.containsKey(key))
            throw new NoDefinedParamWithGivenName(key);
        pushToStack(params.get(key));
    }
    public int getNumberOfValuesInStack() {
        return numberOfValuesInStack;
    }
}
