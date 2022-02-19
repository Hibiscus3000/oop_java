package ru.nsu.fit.oop.calculator.operations;

class AVG extends Operation{
    @Override
    void execute(Context context, String[] args) {
        numberOfArgsCheck(1);
        int i,x = Integer.getInteger(args[0]);
        double summ = 0;
        for (i = 0; i < x; ++i)
            summ += context.popFromStack();
        context.pushToStack(summ/x);
    }
}
