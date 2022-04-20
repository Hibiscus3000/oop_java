package ru.nsu.fit.oop.calculator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommandsParser {
    public List<List<String>> parseCommands(String commandsFileName) throws IOException {
        List<List<String>> commands = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(commandsFileName));
        int i, argStart;
        String line;
        List<String> nameAndArgs;
        while ((line = reader.readLine()) != null) {
            argStart = 0;
            nameAndArgs = new ArrayList<>();
            i = 0;
            while (i != line.length())
                if (!Character.isLetterOrDigit(line.charAt(i))) {
                    nameAndArgs.add(line.substring(argStart, i));
                    argStart = i + 1;
                }
            ++i;
            commands.add(nameAndArgs);
        }
        return commands;
    }
}
