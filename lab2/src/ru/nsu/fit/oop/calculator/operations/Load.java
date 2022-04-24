package ru.nsu.fit.oop.calculator.operations;

import ru.nsu.fit.oop.calculator.exception.NoDefinedParamWithGivenName;
import ru.nsu.fit.oop.calculator.exception.OperationException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

class Load extends Operation{

    public Load() {
        super();
    }

    @Override
    public void execute(Context context, List<String> args) throws OperationException {
        logger.entering(this.getClass().getName(),"execute");
        this.operationName = getClass().getSimpleName();
        this.args = args;
        numberOfArgsCheck(args.size(),1);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(args.get(1)));
            int i, j, beginIndex;
            String line;
            while (null != (line = reader.readLine())) {
                beginIndex = 0;
                for (j = 0; j < line.length(); ++j) {
                    if (Character.isWhitespace(line.charAt(j))) {
                        try {
                            context.pushToStack(Double.parseDouble(line.substring(beginIndex, j - 1)));
                        }
                        catch (NumberFormatException exception) {
                            context.pushNamedParamToStack(line.substring(beginIndex, j - 1));
                        }
                        beginIndex = j + 1;
                    }
                }
            }
        }
        catch (Exception cause) {
            OperationException exception =  new OperationException(operationName, cause);
            logger.throwing(this.getClass().getName(),"execute",exception);
            throw exception;
        }
        finally {
            if (null != reader) {
                try {
                    reader.close();
                    logger.exiting(this.getClass().getName(),"execute");
                }
                catch (IOException exception) {
                    logger.log(Level.WARNING,"Exception catched during closing file performing " +
                            this.getClass().getName());
                }
            }
        }
    }
}
