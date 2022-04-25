package main.java.ru.nsu.fit.oop.calculator.operations;

import main.java.ru.nsu.fit.oop.calculator.exception.OperationException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Save extends Operation{

    public Save() {
        super();
    }

    @Override
    public void execute(Context context, List<String> args) throws OperationException {
        logger.entering(this.getClass().getName(),"execute");
        this.operationName = getClass().getSimpleName();
        this.args = args;
        numberOfArgsCheck(args.size(),1);
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(args.get(1))));
            int i;
            int numberOfValuesInStack = context.getNumberOfValuesInStack();
            for (i = 0; i < numberOfValuesInStack; ++i)
                writer.println(context.popFromStack());
        }
        catch (IOException cause)
        {
            OperationException exception = new OperationException(operationName,cause);
            logger.throwing(this.getClass().getSimpleName(),"execute",exception);
            throw exception;
        }
        finally {
            if (null != writer)
                writer.close();
            logger.exiting(this.getClass().getName(),"execute");
        }
    }
}

