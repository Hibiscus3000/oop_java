package main.java.ru.nsu.fit.oop.calculator.operations;

import main.java.ru.nsu.fit.oop.calculator.exception.NoDefinedParamWithGivenName;
import main.java.ru.nsu.fit.oop.calculator.exception.NotEnoughValuesInStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Context {
    private Stack<Double> args = new Stack<>();
    private Map<String,Double> params = new HashMap<>();

    public void pushToStack(double value){
        args.push(value);
    }
    public double popFromStack() throws NotEnoughValuesInStack {
        if (0 == args.size())
            throw new NotEnoughValuesInStack(1,0);
        return args.pop();
    }
    public double getFromStack() throws NotEnoughValuesInStack {
        if (0 == args.size())
            throw new NotEnoughValuesInStack(1,0);
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
        return args.size();
    }
}
