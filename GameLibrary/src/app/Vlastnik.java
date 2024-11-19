package app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Vlastnik implements Serializable {
    private String name;
    private List<Game> games;

    public Vlastnik(String name) {
        this.name = name;
        this.games = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Game> getGames() {
        return games;
    }

    public void addGame(Game game) {
        if (!games.contains(game)) {
            games.add(game);
        }
    }

    @Override
    public String toString() {
        return String.format("Owner: %s, Games: %s", name, games);
    }
}
