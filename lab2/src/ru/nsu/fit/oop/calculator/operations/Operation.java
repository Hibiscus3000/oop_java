package ru.nsu.fit.oop.calculator.operations;

import ru.nsu.fit.oop.calculator.exception.NotEnoughValuesInStack;
import ru.nsu.fit.oop.calculator.exception.OperationException;
import ru.nsu.fit.oop.calculator.exception.WrongNumberOfArgs;

import java.util.List;

public abstract class Operation {
     protected List<String> args;
     protected String operationName;

     public void numberOfArgsCheck(int realNumberOfArgs, int expectedNumberOfArgs) throws WrongNumberOfArgs {
          if (expectedNumberOfArgs != realNumberOfArgs)
               throw new WrongNumberOfArgs(operationName, args, expectedNumberOfArgs,realNumberOfArgs);
     }

     public void numberOfStackValuesCheck(int realNumberOfStackValues, int expectedNumberOfStackValues)
             throws NotEnoughValuesInStack {
          if (expectedNumberOfStackValues > realNumberOfStackValues)
               throw new NotEnoughValuesInStack(expectedNumberOfStackValues,realNumberOfStackValues);
     }

     public abstract void execute(Context context, List<String> args) throws OperationException;
}
