package com.omar.commands;

import java.util.function.Supplier;

public class ExitCommand extends GenericCommand {
    private final Supplier<Boolean> exit;

    public ExitCommand(Supplier<Boolean> exit) {
        super("exit");
        this.exit = exit;
    }

    @Override
    public void execute(String[] tokens) {
        exit.get();
    }

}
