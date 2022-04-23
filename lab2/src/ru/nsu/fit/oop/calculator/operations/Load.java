package ru.nsu.fit.oop.calculator.operations;

import ru.nsu.fit.oop.calculator.exception.NoDefinedParamWithGivenName;
import ru.nsu.fit.oop.calculator.exception.OperationException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

class Load extends Operation{

    @Override
    public void execute(Context context, List<String> args) throws OperationException {
        this.operationName = getClass().getSimpleName();
        this.args = args;
        numberOfArgsCheck(args.size(),1);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(args.get(0)));
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
        catch (IOException cause)
        {
            throw new OperationException(operationName,cause);
        }
        catch (NoDefinedParamWithGivenName cause) {
            throw new OperationException(operationName, cause);
        }
        finally {
            if (null != reader) {
                try {
                    reader.close();
                }
                catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}
