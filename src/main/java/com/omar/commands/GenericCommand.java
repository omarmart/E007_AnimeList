package com.omar.commands;

public abstract class GenericCommand implements Command {
    private String name;

    public GenericCommand(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
