package ru.nsu.fit.oop.calculator.exception;

public class WrongNumberOfArgsException extends OperationException {

    private String args;
    private int expectedNumberOfArgs, realNumberOfArgs;

    public WrongNumberOfArgsException(String operationName, String[] args, int expectedNumberOfArgs, int realNumberOfArgs) {
        StringBuilder strArgs = new StringBuilder();
        for (String arg : args)
            strArgs.append(arg + " ");
        this.args = new String(strArgs);
        this.realNumberOfArgs = realNumberOfArgs;
        this.expectedNumberOfArgs = expectedNumberOfArgs;
        this.operationName = operationName;
    }

    @Override
    public String getMessage() {
        return new String("Wrong number of arguments in " + operationName + " operation!" +
                "\nExpected number of arguments: " + expectedNumberOfArgs + "\nReal number of arguments: "
                + realNumberOfArgs + "\nArguments are " + args);
    }
}
