package ru.nsu.fit.oop.calculator.operations;

class Define extends OperationWithTextParam
{
    @Override
    void execute(Context context, String[] args) {
        numberOfArgsCheck(2);
        paramNameCheck(args[0]);
        context.defineNamedParam(args[0],Double.parseDouble(args[1]));
    }
}
