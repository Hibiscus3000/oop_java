package main.java.ru.nsu.fit.oop.calculator.operations;

import main.java.ru.nsu.fit.oop.calculator.exception.OperationException;

import java.util.List;

public class Average extends Operation{

    public Average() {
        super();
    }

    @Override
    public void execute(Context context, List<String> args) throws OperationException {
        logger.entering(this.getClass().getName(),"execute");
        this.operationName = getClass().getSimpleName();
        this.args = args;
        numberOfArgsCheck(args.size(),1);
        int x;
        checkArg(args.get(1),false);
        x = Integer.parseInt(args.get(1));
        if (x <= 0) {
            OperationException exception = new OperationException(operationName, "Argument for " +
                    "\"Average\" should be greater than zero");
            logger.throwing(this.getClass().getName(),"execute",exception);
            throw exception;
        }
        numberOfStackValuesCheck(context.getNumberOfValuesInStack(),x);
        double sum = 0;
        int i;
        for (i = 0; i < x; ++i)
            sum += context.popFromStack();
        context.pushToStack(sum / x);
        logger.exiting(this.getClass().getName(),"execute",context.getFromStack());
    }
}
