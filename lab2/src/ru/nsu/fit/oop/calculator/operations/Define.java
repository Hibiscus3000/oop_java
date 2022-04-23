package ru.nsu.fit.oop.calculator.operations;

import ru.nsu.fit.oop.calculator.exception.OperationException;

import java.util.List;

class Define extends Operation
{

    @Override
    public void execute(Context context, List<String> args) throws OperationException {
        this.operationName = getClass().getSimpleName();
        this.args = args;
        numberOfArgsCheck(args.size(),2);
        try {
            context.defineNamedParam(args.get(0), Double.parseDouble(args.get(1)));
        }
        catch (NumberFormatException cause) {
            throw new OperationException(operationName,cause);
        }
    }
}
