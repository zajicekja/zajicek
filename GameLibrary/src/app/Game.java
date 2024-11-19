package app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Game implements Serializable, Comparable<Game> {
    private String title;
    private int year;
    private Rating rating;
    private List<Vlastnik> owners;

    public Game(String title, int year, Rating rating) {
        if (!isValidTitle(title)) {
            throw new IllegalArgumentException("Invalid title");
        }
        if (!isValidYear(year)) {
            throw new IllegalArgumentException("Invalid year");
        }
        this.title = title;
        this.year = year;
        this.rating = rating;
        this.owners = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public Rating getRating() {
        return rating;
    }

    public List<Vlastnik> getOwners() {
        return owners;
    }

    public void addOwner(Vlastnik owner) {
        if (!owners.contains(owner)) {
            owners.add(owner);
        }
    }

    @Override
    public String toString() {
        return String.format("Title: %s, Year: %d, Rating: %s, Owners: %s", title, year, rating, owners);
    }

    @Override
    public int compareTo(Game other) {
        return this.title.compareTo(other.title);
    }

    private boolean isValidTitle(String title) {
        return title != null && !title.trim().isEmpty();
    }

    private boolean isValidYear(int year) {
        String yearPattern = "^(19|20)\\d{2}$";
        return Pattern.matches(yearPattern, String.valueOf(year));
    }
}
