package ru.nsu.fit.oop.calculator;

import ru.nsu.fit.oop.calculator.exception.OperationException;
import ru.nsu.fit.oop.calculator.factory.OperationFactory;
import ru.nsu.fit.oop.calculator.operations.Context;
import ru.nsu.fit.oop.calculator.operations.Operation;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class Calculator {

    public void calculate (BufferedReader commandsReader) {
        CommandsParser parser = new CommandsParser();
        try {
            List<List<String>> commands = parser.parseCommands(commandsReader);
            Context context = new Context();
            for (List<String> command : commands) {
                try {
                    Operation operation = OperationFactory.getInstance().getOperation(command.remove(0));
                    operation.execute(context, command);
                }
                catch (OperationException exception) {
                    if (null != exception.cause) {
                        exception.cause.printStackTrace();
                    }
                    exception.printStackTrace();
                }
            }
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }

    }
}
