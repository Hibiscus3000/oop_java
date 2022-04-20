package ru.nsu.fit.oop.calculator.exception;

public class OperationNotSupported extends OperationException {
    Throwable cause;

    public OperationNotSupported() { super(); }
    public OperationNotSupported(String operationName) {
        super(operationName);
    }
    public OperationNotSupported(String operationName, Throwable cause) {
        super(operationName);
        cause = cause;
    }

    @Override
    public String getMessage() {
        String causeMessage = cause.getMessage();
        return causeMessage + "\nThere is no such supported operation as " + operationName;
    }
}

