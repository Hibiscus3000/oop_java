package ru.nsu.fit.oop.calculator.operations;

import ru.nsu.fit.oop.calculator.exception.OperationException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Save extends Operation{

    @Override
    public void execute(Context context, List<String> args) throws OperationException {
        this.operationName = getClass().getSimpleName();
        this.args = args;
        numberOfArgsCheck(args.size(),1);
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(args.get(0))));
            int i;
            for (i = 0; i < context.getNumberOfValuesInStack(); ++i)
                writer.println(context.popFromStack());
        }
        catch (IOException cause)
        {
            throw new OperationException(operationName,cause);
        }
        finally {
            if (null != writer)
                writer.close();
        }
    }
}

