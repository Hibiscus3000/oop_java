package ru.nsu.fit.oop.calculator.operations;

import ru.nsu.fit.oop.calculator.exception.OperationException;

import java.util.List;

class Average extends Operation{
    @Override
    public void execute(Context context, List<String> args) throws OperationException {
        this.operationName = getClass().getSimpleName();
        this.args = args;
        numberOfArgsCheck(args.size(),1);
        int x = Integer.parseInt(args.get(0));
        numberOfStackValuesCheck(context.getNumberOfValuesInStack(),x);
        double sum = 0;
        int i;
        for (i = 0; i < x; ++i)
            sum += context.popFromStack();
        context.pushToStack(sum / x);
    }
}
