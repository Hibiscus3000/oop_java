package ru.nsu.fit.oop.calculator;

import java.io.IOException;
import java.util.List;

public class Calculator {

    public void calculate (String commandsFileName) throws IOException {
        CommandsParser parser = new CommandsParser();
        List<List<String>> commands = parser.parseCommands(commandsFileName);

    }
}
