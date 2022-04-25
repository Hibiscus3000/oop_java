package main.java.ru.nsu.fit.oop.calculator.operations;

import main.java.ru.nsu.fit.oop.calculator.exception.OperationException;

import java.util.List;

public class Push extends Operation{

    public Push() {
        super();
    }

    @Override
    public void execute(Context context, List<String> args) throws OperationException {
        logger.entering(this.getClass().getName(),"execute");
        this.operationName = getClass().getSimpleName();
        this.args = args;
        numberOfArgsCheck(args.size(),1);
        if ((!Character.isDigit(args.get(1).charAt(0))) && ('-' != args.get(1).charAt(0))) {
                context.pushNamedParamToStack(args.get(1));
        }
        else {
            checkArg(args.get(1),true);
            try {
                context.pushToStack(Double.parseDouble(args.get(1)));
            }
            catch (NumberFormatException cause) {
                OperationException exception = new OperationException(this.getClass().getSimpleName(),
                        cause);
                logger.throwing(this.getClass().getName(),"execute",exception);
                throw exception;
            }
        }
        logger.exiting(this.getClass().getName(),"execute");
    }
}
