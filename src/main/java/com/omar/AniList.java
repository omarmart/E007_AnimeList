package com.omar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AniList {
    private String fileLocation;
    private List<Anime> animeList;

    public AniList(String fileLocation) throws FileNotFoundException, BadFormatException {
        animeList = new ArrayList<>();
        loadAnimeList(fileLocation);
        this.fileLocation = fileLocation;
    }

    public List<Anime> getAniList() {
        return animeList;
    }

    public void loadAnimeList(String fileLocation) throws FileNotFoundException, BadFormatException {
        File file = new File(fileLocation);
        Scanner sc = new Scanner(new FileInputStream(file));

        int position = 0;

        while (sc.hasNextLine()) {
            String nextLine = sc.nextLine();
            String[] parsedLine = nextLine.split(",");

            if (position == 0) {
                position++;
                continue;
            }

            try {
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
            } catch (Exception e) {
                throw new BadFormatException(position); //TODO: preguntar como paso la excepcion al main (para imprimir alli) sin parar de añadir anime
            }
            position++;
        }
    }

    public void updateAnimeFile() {
        //TODO Modificar la linea apropiada del fichero cada vez que se cambie las propiedades de un anime
        FileWriter writer;
        try {
            writer = new FileWriter(fileLocation);

            for (Anime anime : animeList) {
                writer.write(parseAnimeToString(anime));
            }

            writer.close();
        } catch (IOException ex) {

        }
    }

    public String parseAnimeToString(Anime anime) {
        StringWriter aniString = new StringWriter();
        aniString.write(anime.getId());
        aniString.write(anime.getName());
        aniString.write(anime.getStatus().toString());
        aniString.write(anime.getScore());
        aniString.write(anime.getProgress());
        aniString.write(anime.getEpisodes());
        aniString.write(anime.getGenre().toString());

        return aniString.toString();
    }

    // TODO ↓↓↓ MERGE ALL INTO 1 FUNCTION ↓↓↓
    public List<String> searchAnime(String name) {
        List<String> similarNames = new ArrayList<>();

        for (Anime anime : animeList) {
            if (anime.getName().contains(name)) {
                similarNames.add(anime.getName());
            }
        }

        return similarNames;
    }

    public List<String> searchAnime(Genre genre) {
        List<String> nameList = new ArrayList<>();

        for (Anime anime : animeList) {
            if (anime.getGenre() == genre) {
                nameList.add(anime.getName());
            }
        }

        return nameList;
    }

    public List<String> searchAnime(Status status) {
        List<String> nameList = new ArrayList<>();

        for (Anime anime : animeList) {
            if (anime.getStatus() == status) {
                nameList.add(anime.getName());
            }
        }

        return nameList;
    }
    // ↑↑↑ MERGE ALL INTO 1 FUNCTION ↑↑↑

}
