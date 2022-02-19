package ru.nsu.fit.oop.calculator.operations;

class Divide extends Operation {
    @Override
    void execute(Context context, String[] args) {
        numberOfArgsCheck(2);
        context.pushToStack(Double.parseDouble(args[0]) / Double.parseDouble(args[1]));
    }
}