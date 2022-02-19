package ru.nsu.fit.oop.calculator.operations;

class ABS extends Operation {
    @Override
    void execute(Context context, String[] args) {
        numberOfArgsCheck(1);
        context.pushToStack(Math.abs(Double.parseDouble(args[0])));
    }
}