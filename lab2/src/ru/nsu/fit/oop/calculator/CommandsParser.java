package ru.nsu.fit.oop.calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommandsParser {
    public List<List<String>> parseCommands(BufferedReader commandsReader) throws IOException {
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
        return commands;
    }
}
