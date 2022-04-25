package main.java.ru.nsu.fit.oop.calculator.operations;

import main.java.ru.nsu.fit.oop.calculator.exception.*;

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

     protected void numberOfArgsCheck(int realNumberOfArgs, int expectedNumberOfArgs) throws WrongNumberOfArgs {
          if (expectedNumberOfArgs + 1 != realNumberOfArgs) {
               WrongNumberOfArgs exception =  new WrongNumberOfArgs(operationName, args, expectedNumberOfArgs,
                       realNumberOfArgs - 1);
               logger.throwing(Operation.class.getName(),"numberOfArgsCheck",exception);
               throw exception;
          }
     }

     protected void numberOfStackValuesCheck(int realNumberOfStackValues, int expectedNumberOfStackValues)
             throws NotEnoughValuesInStack {
          if (expectedNumberOfStackValues > realNumberOfStackValues) {
               NotEnoughValuesInStack exception = new NotEnoughValuesInStack(expectedNumberOfStackValues, realNumberOfStackValues);
               logger.throwing(Operation.class.getName(),"numberOfStackValuesCheck",exception);
               throw exception;
          }
     }

     protected void checkArg(String arg, boolean isDouble) throws InappropriateArgException {
          int i;
          boolean pointAppeared = false;
          for (i = 0; i < arg.length(); ++i) {
               if (((0 == i) && ('-' == arg.charAt(i))) || (Character.isDigit(arg.charAt(i))))
                    continue;
               if ((!isDouble) || ('.' != arg.charAt(i)) || (pointAppeared))
                    throw new InappropriateArgException(operationName,arg);
               if ('.' == arg.charAt(i))
                    pointAppeared = true;
          }
     }

     public abstract void execute(Context context, List<String> args) throws OperationException;
}
