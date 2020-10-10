package com.omar;

import java.util.function.Consumer;

public class Command {
    private String name;
    private Consumer<String[]> actions;

    public Command(String name, Consumer<String[]> actions) {
        this.name = name;
        this.actions = actions;
    }

    public void execute(String[] tokens) {
        actions.accept(tokens);
    }

    public String getName() {
        return name;
    }

}