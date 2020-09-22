package com.omar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AniList {
    private List<Anime> animeList;

    public AniList(String fileLocation) throws FileNotFoundException {
        animeList = new ArrayList<>();
        loadAnimes(fileLocation);
    }

    public List<Anime> getAniList() {
        return animeList;
    }

    public void loadAnimes(String fileLocation) throws FileNotFoundException {
        File file = new File(fileLocation);
        FileInputStream fileStream;
        Scanner sc;
        fileStream = new FileInputStream(file);

        sc = new Scanner(fileStream);

        while (sc.hasNextLine()) {
            String nextLine = sc.nextLine();
            String[] parsedLine = nextLine.split(",");

            if (parsedLine[0].equals(""))
                continue;

            if (true) { // TODO: verifyLine(parsedLine)
                Anime newAnime;
                String id = parsedLine[0];
                String name = parsedLine[1];
                Status status = Status.valueOf(parsedLine[2]); // Controlar excepcion
                int score = Integer.parseInt(parsedLine[3]); // Controlar excepcion
                int progress = Integer.parseInt(parsedLine[4]);
                int episodes = Integer.parseInt(parsedLine[5]);
                Genre genre = Genre.valueOf(parsedLine[6]);

                newAnime = new Anime(id, name, status, score, progress, episodes, genre);

                animeList.add(newAnime);
            }

        }

        // TODO: Cerrar el scanner
    }

    public Anime getAnimeByName(String name) {
        for (Anime anime : animeList) {
            if (anime.getName().equals(name)) {
                return anime;
            }
        }

        return null;

    }

    public List<String> getSimilarNames(String name) {
        List<String> similarNames = new ArrayList<>();

        for (Anime anime : animeList) {
            if (anime.getName().contains(name)) {
                similarNames.add(anime.getName());
            }
        }

        return similarNames;
    }

    public List<String> getAnimeByGenre(Genre genre) {
        List<String> nameList = new ArrayList<>();

        for (Anime anime : animeList) {
            if (anime.getGenre() == genre) {
                nameList.add(anime.getName());
            }
        }

        return nameList;
    }

    public List<String> getAnimeByStatus(Status status) {
        List<String> nameList = new ArrayList<>();

        for (Anime anime : animeList) {
            if (anime.getStatus() == status) {
                nameList.add(anime.getName());
            }
        }

        return nameList;
    }

}
