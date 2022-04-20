package ru.nsu.fit.oop.calculator.operations;

class Divide extends Operation {
    public Divide(String[] args)
    {
        this.args = args;
        this.operationName = "/";
        this.expectedNumberOfArgs = 1;
    }
    @Override
    void execute(Context context, String[] args) {
        numberOfArgsCheck(args.length);
        context.pushToStack(Double.parseDouble(args[0]) / Double.parseDouble(args[1]));
    }
}