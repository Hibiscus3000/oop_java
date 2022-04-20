package ru.nsu.fit.oop.calculator.operations;

class Define extends OperationWithTextParam
{
    public Define(String[] args)
    {
        this.args = args;
        this.operationName = "AVG";
        this.expectedNumberOfArgs = 1;
    }
    @Override
    void execute(Context context, String[] args) {
        numberOfArgsCheck(args.length);
        paramNameCheck(args[0]);
        context.defineNamedParam(args[0],Double.parseDouble(args[1]));
    }
}
