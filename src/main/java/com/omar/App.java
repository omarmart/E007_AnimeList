package com.omar;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

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

    private static void showMenu() {
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

    private static void loadCommands(AniList aniList, CommandExecutor cExecutor) {
        cExecutor.registerCommand(new Command("exit", (t) -> {
            executing = false;
        }));

        cExecutor.registerCommand(new Command("help", (t) -> {
            showMenu();
        }));

        cExecutor.registerCommand(new Command("list", (t) -> {
            listAnime(aniList, t);
        }));

        cExecutor.registerCommand(new Command("change", (t) -> {
            changeAnime(aniList, t);
        }));

        cExecutor.registerCommand(new Command("show", (t) -> {
            showAnime(aniList, t);
        }));
    }

    public static void showAnime(AniList aniList, String[] t) {
        if (t.length != 2) {
            throw new CommandFormatException("The correct use for this command is: show [animeID]");
        }

        try {
            int animeId = Integer.parseInt(t[1]);
            printAnime(aniList.getAniList().get(animeId - 1));
        } catch (NumberFormatException e) {
            System.out.println("Please insert only numbers after the show command");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("This is not a valid id");
        }
    }

    private static void listAnime(AniList aniList, String[] tokens) {
        Predicate<Anime> filters = (anime) -> true;

        Map<String, Function<String, Predicate<Anime>>> searchCommands = new HashMap<>();
        searchCommands.put("-anime", (name) -> (anime) -> anime.getName().toLowerCase().contains(name.toLowerCase()));
        searchCommands.put("-genre", (genre) -> (anime) -> anime.getGenre() == Genre.valueOf(genre));
        searchCommands.put("-status", (status) -> (anime) -> anime.getStatus() == Status.valueOf(status));

        try {
            for (int i = 0; i < tokens.length; i++) {
                Function<String, Predicate<Anime>> filterFactory = searchCommands.get(tokens[i]);
                if (filterFactory != null) {
                    if (i + 1 >= tokens.length) {
                        throw new CommandFormatException("Missing parameter for argument " + tokens[i]);
                    }
                    filters = filters.and(filterFactory.apply(tokens[i + 1]));
                }
            }

            printAnilist(aniList.searchAnime(filters));

        } catch (IllegalArgumentException illE) {
            System.out.println("Status/Genre is not a valid");
        }
    }

    private static void changeAnime(AniList aniList, String[] tokens) { //TODO: mejorar esta parte del codigo
        try {
            int animeId = Integer.parseInt(tokens[1]);
            Anime toChange = aniList.getAniList().get(animeId - 1);

            for (int i = 0; i < tokens.length; i++) {
                switch (tokens[i]) {
                    case "-status":
                        Status newStatus = Status.valueOf(tokens[i + 1]);
                        toChange.setStatus(newStatus);
                        break;
                    case "-score":
                        int newScore = Integer.parseInt(tokens[i + 1]);
                        if (newScore >= 0 && newScore <= 10) {
                            toChange.setScore(newScore);
                        } else {
                            throw new CommandFormatException("Score must be between 0 an 10");
                        }
                        break;

                    case "-progress":
                        int newProgress = Integer.parseInt(tokens[i + 1]);
                        if (newProgress >= 0 && newProgress <= toChange.getEpisodes()) {
                            toChange.setProgress(newProgress);
                        } else {
                            throw new CommandFormatException(
                                    "Progress must be between 0 and " + toChange.getEpisodes());
                        }

                        break;
                }
            }

            aniList.updateAnimeList(toChange);
            aniList.updateAnimeFile();
            System.out.println("Change succesfull!");

        } catch (NumberFormatException numE) {
            System.out.println("Please insert only numbers after change/progress/score");
        } catch (IndexOutOfBoundsException indE) {
            System.out.println(tokens[1] + " is not a valid id");
        } catch (IllegalArgumentException illE) {
            System.out.println("Status is not a valid");
        } catch (IOException e) {
            System.out.println("Anime file not found");
        }
    }

    public static void printAnime(Anime anime) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("#%s %s (%s)\n", anime.getId(), anime.getName(), anime.getStatus().toString()));
        sb.append("  Progress: ").append(anime.getProgress()).append("/").append(anime.getEpisodes()).append("\n");
        sb.append("  Score: ").append(anime.getScore()).append("\n");
        sb.append("  Genre: ").append(anime.getGenre()).append("\n");
        System.out.println(sb.toString());
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
