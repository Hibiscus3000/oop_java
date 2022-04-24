package ru.nsu.fit.oop.calculator.operations;

import ru.nsu.fit.oop.calculator.exception.NoDefinedParamWithGivenName;
import ru.nsu.fit.oop.calculator.exception.OperationException;

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
        try {
            context.pushToStack(Double.parseDouble(args.get(1)));
        }
        catch (NumberFormatException exception) {
            try {
                context.pushNamedParamToStack(args.get(1));
            }
            catch (NoDefinedParamWithGivenName cause) {
                throw new OperationException(operationName,cause);
            }
        }
        logger.exiting(this.getClass().getName(),"execute");
    }
}
