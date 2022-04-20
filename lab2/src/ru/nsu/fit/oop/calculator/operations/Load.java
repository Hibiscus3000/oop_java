package ru.nsu.fit.oop.calculator.operations;

import java.io.FileWriter;
import java.io.IOException;

class Load extends OperationWithTextParam{
    @Override
    void execute(Context context, String[] args) {
        numberOfArgsCheck(1,args.length);
        paramNameCheck(args[0]);
        FileWriter writer = null;
        try {
            writer = new FileWriter(args[0]);
            int i, numberOfArgsInStack = context.getNumberOfArgsInStack();
            for (i = 0; i < numberOfArgsInStack; ++i)
                writer.write(String.valueOf(context.popFromStack()) + "\n");
        }
        catch (IOException ex)
        {
            System.err.println("Error while reading file: " + ex.getMessage());
        }
        finally {
            if (null != writer)
                try
                {
                    writer.close();
                }
                catch (IOException ex) {
                    ex.printStackTrace();
            }
        }
    }
}
