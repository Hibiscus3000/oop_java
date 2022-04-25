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
                "\nSecond argument for define operation should be a double.\nArgument for " +
                "average operation should be a number a number of stack values which from wich " +
                "average will be taken from, therefor an integer.";
    }
}
