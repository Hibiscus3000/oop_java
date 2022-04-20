package ru.nsu.fit.oop.calculator.operations;

class Minus extends Operation {
    @Override
    void execute(Context context, String[] args) {
        numberOfArgsCheck(2,args.length);
        context.pushToStack(Double.parseDouble(args[0]) - Double.parseDouble(args[1]));
    }
}
