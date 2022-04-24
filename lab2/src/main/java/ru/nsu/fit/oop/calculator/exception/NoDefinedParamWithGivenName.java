package main.java.ru.nsu.fit.oop.calculator.exception;

public class NoDefinedParamWithGivenName extends OperationException{
    String paramName;

    public NoDefinedParamWithGivenName(String paramName) {
        this.paramName = paramName;
    }

    @Override
    public String getMessage() {
        return "Parameter with given name wasn't define \"" + paramName + "\".";
    }
}
