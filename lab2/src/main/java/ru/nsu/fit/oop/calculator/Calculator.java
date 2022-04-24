package main.java.ru.nsu.fit.oop.calculator;

import main.java.ru.nsu.fit.oop.calculator.exception.OperationException;
import main.java.ru.nsu.fit.oop.calculator.factory.OperationFactory;
import main.java.ru.nsu.fit.oop.calculator.operations.Context;
import main.java.ru.nsu.fit.oop.calculator.operations.Operation;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Calculator {

    private static final Logger logger = Logger.getLogger(Calculator.class.getName());

    public void calculate (BufferedReader commandsReader) {
        logger.setLevel(Level.FINEST);
        logger.entering(this.getClass().getName(),"calculate");
        CommandsParser parser = new CommandsParser();
        try {
            List<List<String>> commands = parser.parseCommands(commandsReader);
            Context context = new Context();
            for (List<String> command : commands) {
                try {
                    Operation operation = OperationFactory.getInstance().getOperation(command.get(0));
                    operation.execute(context, command);
                    logger.log(Level.INFO,"Performed \"" + operation.getClass().getSimpleName() + "\" successfully");
                }
                catch (OperationException exception) {
                    if (null != exception.cause) {
                        exception.getMessage();
                        logger.log(Level.WARNING,"Catched exception while performing \"" + command.get(0) + "\"",
                                exception.cause);
                    }
                    else {
                        logger.log(Level.WARNING, "Catched exception while performing \"" + command.get(0) + "\"",
                                exception);
                    }
                }
            }
            logger.exiting(this.getClass().getName(),"calculate");
        }
        catch (IOException exception) {
            logger.log(Level.SEVERE,"Catched exception while parsing commands and making operations",exception);
        }

    }
}
