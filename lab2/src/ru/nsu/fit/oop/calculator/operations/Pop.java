package ru.nsu.fit.oop.calculator.operations;

import ru.nsu.fit.oop.calculator.exception.OperationException;

import java.util.List;

public class Pop extends Operation{

    @Override
    public void execute(Context context, List<String> args) throws OperationException {
        this.operationName = getClass().getSimpleName();
        this.args = args;
        numberOfStackValuesCheck(context.getNumberOfValuesInStack(),1);
        context.popFromStack();
    }
}
