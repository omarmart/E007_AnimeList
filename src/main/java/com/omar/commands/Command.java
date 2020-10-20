package com.omar.commands;

public interface Command {
    public void execute(String[] tokens);

    public String getName();

}