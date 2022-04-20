package ru.nsu.fit.oop.calculator.operations;

class Multiply extends Operation {
    @Override
    void execute(Context context, String[] args) {
        numberOfArgsCheck(args.length);
        double arg1 = context.popFromStack(), arg2 = context.popFromStack();
        context.pushToStack(arg1*arg2);
    }
}