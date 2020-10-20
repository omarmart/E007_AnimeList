package com.omar.commands;

public class HelpCommand extends GenericCommand {

    public HelpCommand() {
        super("help");
    }

    @Override
    public void execute(String[] tokens) {
        StringBuilder sb = new StringBuilder();
        sb.append("Available commands: \n\n");

        sb.append("exit: Exits the program\n\n");

        sb.append("help: shows the available commands\n\n");

        sb.append("show <animeID>: Shows the properties of an anime\n\n");

        sb.append("list [filters]: Shows a list of anime (Id and name) depending on the subcommands\n");
        sb.append("Filters may be any combination of:\n");
        sb.append("    -anime <animeName>:Searches anime with similar name to <animeName>. \n");
        sb.append("                       Surrounding quotes may be added to ignore anime name whitespaces. \n");
        sb.append("    -genre <genre>: Searches anime with the specified genre\n");
        sb.append("    -status <status>: Searches anime with the specified status\n\n");

        sb.append("change <animeID> [changeOptions]: Changes the properties of the specified anime\n");
        sb.append("ChangeOptions may be any combination of:\n");
        sb.append("    -status <status>: Changes the status\n");
        sb.append("    -score <number>: Changes the score\n");
        sb.append("    -progress <number>: Changes the episode progress\n\n");
        System.out.println(sb.toString());
    }

}
