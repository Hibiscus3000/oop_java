package ru.nsu.fit.oop.calculator.operations;

class Average extends Operation{
    public Average(String[] args)
    {
        this.args = args;
        this.operationName = "AVG";
        this.expectedNumberOfArgs = 1;
    }
    @Override
    void execute(Context context, String[] args) {
        numberOfArgsCheck(args.length);
        int i,x = Integer.getInteger(args[0]);
        double summ = 0;
        for (i = 0; i < x; ++i)
            summ += context.popFromStack();
        context.pushToStack(summ/x);
    }
}
