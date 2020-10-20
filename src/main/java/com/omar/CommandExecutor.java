package com.omar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.omar.commands.Command;

public class CommandExecutor { //TODO: Hacer tests 
    Map<String, Command> commands = new HashMap<>();
    //almacenar/ejecutar comandos

    public void registerCommand(Command command) {
        commands.put(command.getName(), command);
    }

    public void executeCommand(String input) {
        String[] tokens = splitExceptQuotes(input);
        String command = tokens[0];

        Command consumer = commands.get(command);
        if (consumer == null) {
            System.out.println("Unknown command " + command);
            return;
        }

        try {
            consumer.execute(tokens);
        } catch (CommandFormatException e) {
            System.out.println("Unable to execute command"); //no utilizar clases estaticas
            System.out.println(e.getMessage());
        }
    }

    public static String[] splitExceptQuotes(String orig) {
        if (orig == null) {
            throw new IllegalArgumentException("Null input value");
        }

        List<String> result = new ArrayList<>();
        StringBuilder fragment = new StringBuilder();
        boolean insideQuotes = false;

        for (char c : orig.toCharArray()) {

            if (c == ' ' && !insideQuotes) {
                if (fragment.length() != 0) {
                    result.add(fragment.toString());
                    fragment = new StringBuilder();
                }
            } else if (c == '"') {
                insideQuotes = !insideQuotes;
            } else {
                fragment.append(c);
            }
        }

        if (fragment.length() != 0) {
            result.add(fragment.toString());
        }

        return result.toArray(new String[0]);
    }

}
