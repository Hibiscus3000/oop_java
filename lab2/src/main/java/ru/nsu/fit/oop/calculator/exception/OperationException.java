package main.java.ru.nsu.fit.oop.calculator.exception;

public class OperationException extends Exception
{
    protected String operationName;
    protected String error = null;
    public Throwable cause = null;

    public OperationException() {
        super();
    }
    public OperationException(String operationName) {
        this.operationName = operationName;
    }
    public OperationException(String operationName,String error) {
        this.operationName = operationName;
        this.error = error;
    }
    public OperationException(String operationName, Throwable cause) {
        this.cause = cause;
        this.operationName = operationName;
    }

    @Override
    public String getMessage() {
        if (null != error)
            return "Error during \"" + operationName + "\" execution appeared.\n" + error;
        else
            return "Error during \"" + operationName + "\" execution appeared.";
    }
}

