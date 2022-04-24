package main.java.ru.nsu.fit.oop.calculator.exception;

import java.util.List;

public class WrongNumberOfArgs extends OperationException {

    private String args;
    private int expectedNumberOfArgs, realNumberOfArgs;

    public WrongNumberOfArgs(String operationName, List<String> args, int expectedNumberOfArgs,
                             int realNumberOfArgs) {
        StringBuilder strArgs = new StringBuilder();
        for (String arg : args) {
            strArgs.append("\"");
            strArgs.append(arg);
            strArgs.append("\" ");
        }
        this.args = new String(strArgs);
        this.realNumberOfArgs = realNumberOfArgs;
        this.expectedNumberOfArgs = expectedNumberOfArgs;
        this.operationName = operationName;
    }

    @Override
    public String getMessage() {
        return "Wrong number of arguments in \"" + operationName + "\" operation!" +
                "\nExpected number of arguments: " + expectedNumberOfArgs + "\nReal number of arguments: "
                + realNumberOfArgs + "\nArguments are " + args + '.';
    }
}
