package com.omar;

public class Anime {

    private String id;
    private String name;
    private Status status;
    private int score;
    private int progress;
    private int episodes;
    private Genre genre;

    public Anime(String id, String name, Status status, int score, int progress, int episodes, Genre genre) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.score = score;
        this.progress = progress;
        this.episodes = episodes;
        this.genre = genre;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    public int getScore() {
        return score;
    }

    public int getProgress() {
        return progress;
    }

    public int getEpisodes() {
        return episodes;
    }

    public Genre getGenre() {
        return genre;
    }

}
