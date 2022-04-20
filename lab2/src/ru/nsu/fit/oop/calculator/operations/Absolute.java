package ru.nsu.fit.oop.calculator.operations;

class Absolute extends Operation {

    public Absolute(String[] args)
    {
        this.args = args;
        this.operationName = "ABS";
        this.expectedNumberOfArgs = 1;
    }
    @Override
    void execute(Context context) {
        numberOfArgsCheck(args.length);
        context.pushToStack(Math.abs(Double.parseDouble(args[0])));
    }
}