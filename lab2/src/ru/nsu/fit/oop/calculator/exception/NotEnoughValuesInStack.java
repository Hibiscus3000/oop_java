package ru.nsu.fit.oop.calculator.exception;

public class NotEnoughValuesInStack extends OperationException{
    private int expectedNumberOfValues;
    private int realNumberOfValues;
    public NotEnoughValuesInStack(int expectedNumberOfValues, int realNumberOfValues)
    {

        this.realNumberOfValues = realNumberOfValues;
        this.expectedNumberOfValues = expectedNumberOfValues;
    }

    @Override
    public String getMessage() {
        return "Not enough values in stack for \"" + operationName + "\"\nExpected " +
                expectedNumberOfValues + " values, but only " + realNumberOfValues +
                " turned out to be in stack";
    }
}
