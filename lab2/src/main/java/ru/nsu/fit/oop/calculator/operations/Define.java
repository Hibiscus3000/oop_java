package main.java.ru.nsu.fit.oop.calculator.operations;

import main.java.ru.nsu.fit.oop.calculator.exception.InappropriateParameterName;
import main.java.ru.nsu.fit.oop.calculator.exception.OperationException;

import java.util.List;

public class Define extends Operation
{

    public Define() {
        super();
    }

    @Override
    public void execute(Context context, List<String> args) throws OperationException {
        logger.entering(this.getClass().getName(),"execute");
        this.operationName = getClass().getSimpleName();
        this.args = args;
        numberOfArgsCheck(args.size(),2);
        if (Character.isDigit(args.get(1).charAt(0)))
            throw new InappropriateParameterName(args.get(1));
        checkArg(this.getClass().getSimpleName(),args.get(2));
        try {
            context.defineNamedParam(args.get(1), Double.parseDouble(args.get(2)));
        }
        catch (NumberFormatException cause) {
            OperationException exception = new OperationException(operationName,cause);
            logger.throwing(this.getClass().getName(),"execute",exception);
            throw exception;
        }
        logger.exiting(this.getClass().getName(),"execute");
    }
}
