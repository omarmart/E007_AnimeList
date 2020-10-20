package com.omar;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import com.omar.commands.ChangeCommand;
import com.omar.commands.ExitCommand;
import com.omar.commands.HelpCommand;
import com.omar.commands.ListCommand;
import com.omar.commands.ShowCommand;

public class App {

    private static Scanner sc = new Scanner(System.in);
    private static boolean executing = true;

    public static void main(String[] args) {

        try {
            AniList aniList = new AniList("AnimeList.csv");
            System.out.println("Type 'help' to see the command list.");

            CommandExecutor commandExecutor = new CommandExecutor();
            loadCommands(aniList, commandExecutor);

            while (executing) {
                String input = sc.nextLine();
                commandExecutor.executeCommand(input);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found, restart and try again");
        } catch (BadFormatException e) {
            System.out.println("File not formatted propperly");
            System.out.println("Line with error: " + e.getBadLine());
        }
    }

    private static void loadCommands(AniList aniList, CommandExecutor cExecutor) {
        cExecutor.registerCommand(new ExitCommand(() -> {
            executing = false;
            return true;
        }));
        cExecutor.registerCommand(new HelpCommand());
        cExecutor.registerCommand(new ListCommand(aniList));
        cExecutor.registerCommand(new ChangeCommand(aniList));
        cExecutor.registerCommand(new ShowCommand(aniList));
    }

    public static void printAnilist(List<Anime> animeList) {
        if (animeList.size() == 0) {
            System.out.println("No anime with these parameters was found.");
            return;
        }

        for (Anime anime : animeList) {
            System.out.println(String.format("#%s %s", anime.getId(), anime.getName()));
        }
    }

}