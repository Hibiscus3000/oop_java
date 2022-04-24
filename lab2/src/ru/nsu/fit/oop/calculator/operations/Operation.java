package ru.nsu.fit.oop.calculator.operations;

import ru.nsu.fit.oop.calculator.exception.NotEnoughValuesInStack;
import ru.nsu.fit.oop.calculator.exception.OperationException;
import ru.nsu.fit.oop.calculator.exception.WrongNumberOfArgs;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Operation {

     protected static final Logger logger = Logger.getLogger(Operation.class.getName());

     protected List<String> args;
     protected String operationName;

     public Operation() {
          logger.setLevel(Level.FINEST);
     }

     public void numberOfArgsCheck(int realNumberOfArgs, int expectedNumberOfArgs) throws WrongNumberOfArgs {
          if (expectedNumberOfArgs + 1 != realNumberOfArgs) {
               WrongNumberOfArgs exception =  new WrongNumberOfArgs(operationName, args, expectedNumberOfArgs,
                       realNumberOfArgs);
               logger.throwing(Operation.class.getName(),"numberOfArgsCheck",exception);
               throw exception;
          }
     }

     public void numberOfStackValuesCheck(int realNumberOfStackValues, int expectedNumberOfStackValues)
             throws NotEnoughValuesInStack {
          if (expectedNumberOfStackValues > realNumberOfStackValues) {
               NotEnoughValuesInStack exception = new NotEnoughValuesInStack(expectedNumberOfStackValues, realNumberOfStackValues);
               logger.throwing(Operation.class.getName(),"numberOfStackValuesCheck",exception);
               throw exception;
          }
     }

     public abstract void execute(Context context, List<String> args) throws OperationException;
}
