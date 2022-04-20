package ru.nsu.fit.oop.calculator.exception;

public class NoDefinedParamWithGivenName extends Exception{
    String paramName;

    public NoDefinedParamWithGivenName() { super(); }
    public NoDefinedParamWithGivenName(String paramName) {
        this.paramName = paramName;
    }

    @Override
    public String getMessage() {
        return new String("Name of this parameter wasn't defined: " + paramName);
    }
}
