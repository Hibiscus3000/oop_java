package main.java.ru.nsu.fit.oop.calculator.exception;

public class OperationNotSupported extends OperationException {

    public OperationNotSupported() { super(); }
    public OperationNotSupported(String operationName) {
        super(operationName);
    }
    public OperationNotSupported(String operationName, Throwable cause) {
        super(operationName,cause);
    }

    @Override
    public String getMessage() {
            return "Operation \"" + operationName + "\" is not supported.";
    }
}

