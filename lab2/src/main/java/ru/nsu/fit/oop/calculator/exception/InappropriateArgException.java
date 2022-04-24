package main.java.ru.nsu.fit.oop.calculator.exception;

public class InappropriateArgException extends OperationException{
    String arg;

    public InappropriateArgException(String operationName,String arg) {
        super(operationName);
        this.arg = arg;
    }

    public String getMessage() {
        return "Inappropriate argument for \"" + operationName + "\" operation: \"" + arg + "\"" +
                "\nAn argument for push operation should either be a double, or a pre-defined" +
                " named parameter, thus should not start with a digit." +
                "\nSecond argument for define should be a double.";
    }
}
