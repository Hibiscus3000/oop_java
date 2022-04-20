package ru.nsu.fit.oop.calculator.exception;

class OperationException extends Exception
{
    protected String operationName;
    OperationException() {
        super();
    }
    OperationException(String operationName) {
        this.operationName = operationName;
    }
}

