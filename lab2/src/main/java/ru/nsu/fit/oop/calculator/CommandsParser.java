package main.java.ru.nsu.fit.oop.calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandsParser {

    private static final Logger logger = Logger.getLogger(CommandsParser.class.getName());

    public List<List<String>> parseCommands(BufferedReader commandsReader) throws IOException {
        logger.setLevel(Level.FINEST);
        logger.entering(this.getClass().getName(),"parseCommands");
        List<List<String>> commands = new ArrayList<>();
        int i, argStart;
        String line;
        List<String> nameAndArgs;
        while (((line = commandsReader.readLine()) != null) && (0 != line.length())) {
            argStart = 0;
            nameAndArgs = new ArrayList<>();
            i = 0;
            while (i != line.length()) {
                if (Character.isWhitespace(line.charAt(i))) {
                    nameAndArgs.add(line.substring(argStart, i));
                    argStart = i + 1;
                }
                ++i;
            }
            if (argStart != i)
                nameAndArgs.add(line.substring(argStart));
            commands.add(nameAndArgs);
        }
        logger.exiting(this.getClass().getName(),"parseCommands");
        return commands;
    }
}
