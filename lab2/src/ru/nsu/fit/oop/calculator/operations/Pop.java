package ru.nsu.fit.oop.calculator.operations;

import ru.nsu.fit.oop.calculator.exception.OperationException;

import java.util.List;

public class Pop extends Operation{

    public Pop() {
        super();
    }

    @Override
    public void execute(Context context, List<String> args) throws OperationException {
        logger.entering(this.getClass().getName(),"execute");
        this.operationName = getClass().getSimpleName();
        this.args = args;
        numberOfStackValuesCheck(context.getNumberOfValuesInStack(),1);
        context.popFromStack();
        logger.exiting(this.getClass().getName(),"execute");
    }
}
