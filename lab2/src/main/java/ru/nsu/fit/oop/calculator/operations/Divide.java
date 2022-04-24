package main.java.ru.nsu.fit.oop.calculator.operations;

import main.java.ru.nsu.fit.oop.calculator.exception.OperationException;

import java.util.List;

public class Divide extends Operation {

    public Divide() {
        super();
    }

    @Override
    public void execute(Context context, List<String> args) throws OperationException {
        logger.entering(this.getClass().getName(),"execute");
        this.operationName = getClass().getSimpleName();
        this.args = args;
        numberOfStackValuesCheck(context.getNumberOfValuesInStack(),2);
        double divider = context.popFromStack();
        double dividend = context.popFromStack();
        context.pushToStack( dividend / divider );
        logger.exiting(this.getClass().getName(),"execute");
    }
}