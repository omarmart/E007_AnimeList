package com.omar;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

public class App {

    private static Scanner sc = new Scanner(System.in);
    private static boolean executing = true;

    public static void main(String[] args) {
        try {
            AniList aniList = new AniList("AnimeList.csv");
            showMenu();

            while (executing) {
                String input = sc.nextLine();
                processCommand(aniList, input);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found, restart and try again");
        } catch (BadFormatException e) {
            System.out.println("File not formatted propperly");
            System.out.println("Line with error: " + e.getBadLine());
        }
    }

    private static void showMenu() {
        System.out.println("Available commands: ");

        System.out.println("exit: Exits the program");
        System.out.println("");

        System.out.println("show [animeID]: Shows the properties of an anime");
        System.out.println("");

        System.out.println("search: Shows a list of anime (Id and name) depending on the subcommands");
        System.out.println("    -anime \"[animeName]\":Searches anime with similar name to [animeName]");
        System.out.println("    -genre [genre]: Searches anime with the specified genre");
        System.out.println("    -status [status]: Searches anime with the specified status");
        System.out.println("");

        System.out.println("change [animeID]: Changes the properties of the specified anime");
        System.out.println("    -status [status]: Changes the status");
        System.out.println("    -score [number]: Changes the score");
        System.out.println("    -progress [number]: Changes the episode progress");

        // Obligar a los usuarios a que metan el nombre de los animes dentro de ""
    }

    private static void processCommand(AniList aniList, String input) {
        String[] tokens = input.split(" ");
        String command = tokens[0];

        switch (command) {
            case "exit":
                executing = false;
                return;

            case "show":
                if (tokens.length == 2) {
                    try {
                        int animeId = Integer.parseInt(tokens[1]);
                        printAnime(aniList.getAniList().get(animeId - 1));
                    } catch (NumberFormatException e) {
                        System.out.println("Please insert only numbers after the show command");
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("This is not a valid id");
                    }
                }
                break;

            case "search":
                search(aniList, input, tokens);
                break;

            case "change":
                changeAnime(aniList, tokens);
                break;

            default:
                System.out.println("Unknown command " + command);
                break;
        }
    }

    private static void search(AniList aniList, String input, String[] tokens) {
        Optional<AniList> filtered = Optional.empty();
        try {
            for (int i = 0; i < tokens.length; i++) {
                switch (tokens[i]) {
                    case "-anime":
                        //TODO comprobar que no me meten mas de dos "
                        String searchName = input.substring(input.indexOf("\"") + 1, input.lastIndexOf("\""));
                        if (filtered.isPresent()) {
                            filtered.of(filtered.get().searchAnime(searchName));
                        } else {
                            filtered.of(aniList.searchAnime(searchName));
                        }
                        break;
                    case "-genre":
                        Genre searchGenre = Genre.valueOf(tokens[i + 1]);
                        if (filtered.isPresent()) {
                            filtered.of(filtered.get().searchAnime(searchGenre));
                        } else {
                            filtered.of(aniList.searchAnime(searchGenre));
                        }
                        break;

                    case "-status":
                        Status searchStatus = Status.valueOf(tokens[i + 1]);
                        if (filtered.isPresent()) {
                            filtered.of(filtered.get().searchAnime(searchStatus));
                        } else {
                            filtered.of(aniList.searchAnime(searchStatus));
                        }
                        break;
                }
            }

            //TODO: Print id/name of filtered list

        } catch (IllegalArgumentException illE) {
            System.out.println("Status is not a valid");
        }
    }

    private static void changeAnime(AniList aniList, String[] tokens) {
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
                            System.out.println("Score must be between 0 an 10");
                        }
                        break;

                    case "-progress":
                        int newProgress = Integer.parseInt(tokens[i + 1]);
                        if (newProgress >= 0 && newProgress <= toChange.getEpisodes()) {
                            toChange.setProgress(newProgress);
                        } else {
                            System.out.println("Progress must be between 0 and " + toChange.getEpisodes());
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
        System.out.println("- ↓ -----ANIME INFO----- ↓ -");
        System.out.println("Id: " + anime.getId());
        System.out.println("Name: " + anime.getName());
        System.out.println("Status: " + anime.getStatus());
        System.out.println("Score: " + anime.getScore());
        System.out.println("Progress: " + anime.getProgress());
        System.out.println("Episodes: " + anime.getEpisodes());
        System.out.println("Genre: " + anime.getGenre());
        System.out.println("- ↑ -----ANIME INFO----- ↑ -");
    }

}
