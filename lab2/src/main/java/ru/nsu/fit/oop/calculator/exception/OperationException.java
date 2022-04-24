package main.java.ru.nsu.fit.oop.calculator.exception;

public class OperationException extends Exception
{
    protected String operationName;
    public Throwable cause = null;

    public OperationException() {
        super();
    }
    public OperationException(String operationName) {
        this.operationName = operationName;
    }
    public OperationException(String operationName, Throwable cause) {
        this.cause = cause;
        this.operationName = operationName;
    }

    @Override
    public String getMessage() {
            return "Error during \"" + operationName + "\" execution appeared.";
    }
}

