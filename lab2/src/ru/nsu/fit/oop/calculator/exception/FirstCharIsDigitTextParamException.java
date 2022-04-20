package ru.nsu.fit.oop.calculator.exception;

public class FirstCharIsDigitTextParamException extends OperationException {

    private String param;

    public FirstCharIsDigitTextParamException(String operationName, String param) {
        this.operationName = operationName;
        this.param = param;
    }

    @Override
    public String getMessage() {
        return new String("First character in text parameter of " + operationName
                + " shouldn't be a digit!\nGiven parameter name: " + param);
    }
}
