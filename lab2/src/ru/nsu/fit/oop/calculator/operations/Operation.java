package ru.nsu.fit.oop.calculator.operations;

import ru.nsu.fit.oop.calculator.exception.WrongNumberOfArgsException;

public abstract class Operation {
     protected String operationName;
     protected String[] args;
     int expectedNumberOfArgs;
     public void numberOfArgsCheck(int realNumberOfArgs) throws WrongNumberOfArgsException {
          if (expectedNumberOfArgs != realNumberOfArgs)
               throw new WrongNumberOfArgsException(operationName, args,expectedNumberOfArgs,realNumberOfArgs);
     }
     abstract void execute(Context context);
}
