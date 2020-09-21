package com.omar;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Introduce the name of the file: ");
        String input = sc.nextLine();

        try {
            AniList aniList = new AniList(input);
            showMenu(aniList);
        } catch (FileNotFoundException e) {
            System.out.println("File not found, restart and try again");
        }
    }

    private static void showMenu(AniList aniList) {
        System.out.println("Available commands: ");
        System.out.println("search [anime]: Shows the properties of anime, or similar anime names if one is not found");
        System.out.println("search -genre [genre]: Shows anime with the specified genre");
        System.out.println("search -status [status]: Shows anime with the specified status");
        System.out.println("exit: Exits the program");

        //cambio

        // poder meter search -anime Shingeki -status COMPLETED -genre action
        // poder buscar el anime por id
        // search para buscar el anime
        // show para mostrar la info de un anime
        // Obligar a los usuarios a que metan el nombre de los animes dentro de ""

        while (true) {
            String input = sc.nextLine();
            processCommand(aniList, input);
        }
    }

    private static void processCommand(AniList aniList, String input) {
        String[] tokens = input.split(" ");
        String command = tokens[0];
        switch (command) {
            case "exit":
                return;
            case "search":
                if (tokens.length == 2) {
                    String animeName = tokens[1];
                    Anime anime = aniList.getAnimeByName(animeName);

                    if (anime != null) {
                        printAnime(anime);
                    } else {
                        List<String> similarNames = aniList.getSimilarNames(animeName);
                        System.out.println("Anime not found, anime with similar names:");
                        for (String name : similarNames) {
                            System.out.println(name);
                        }
                    }

                } else {

                    String filter = tokens[1];
                    switch (filter) {
                        case "-genre":

                            break;

                        case "-status":
                            break;

                    }
                }
                break;

            // TODO case change (-status, -progress)
            default:
                System.out.println("Unknown command " + command);
                break;
        }
    }

    public static void printAnime(Anime anime) {
        System.out.println("Name: " + anime.getName());
        System.out.println("Status: " + anime.getStatus());
        System.out.println("Score: " + anime.getScore());
        System.out.println("Progress: " + anime.getProgress());
        System.out.println("Episodes: " + anime.getEpisodes());
        System.out.println("Genre: " + anime.getGenre());
    }

}
