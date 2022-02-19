package ru.nsu.fit.oop.calculator.operations;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Context {
    private int numberOfArgsInStack = 0;
    private Stack<Double> args = new Stack<>();
    private Map<String,Double> params = new HashMap<>();

    public void pushToStack(double value)
    {
        ++numberOfArgsInStack;
        args.push(value);
    }
    public double popFromStack() {
        return args.pop();
    }
    public void defineNamedParam(String key, double value)
    {
        params.put(key,value);
    }
    public int getNumberOfArgsInStack() { return numberOfArgsInStack;}
}
