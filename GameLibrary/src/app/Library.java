package app;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Library {
    private List<Game> games;
    private List<Vlastnik> owners;

    public Library() {
        games = new ArrayList<>();
        owners = new ArrayList<>();
    }

    public void addGame(Game game) {
        games.add(game);
    }

    public List<Game> getGames() {
        return games;
    }

    public void addOwner(Vlastnik owner) {
        owners.add(owner);
    }

    public List<Vlastnik> getOwners() {
        return owners;
    }

    public boolean loadGamesFromFile(String fileName) {
        Path filePath = Paths.get("src", "user_files", fileName);
        try (Scanner scanner = new Scanner(filePath)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String title = parts[0].trim();
                    int year = Integer.parseInt(parts[1].trim());
                    Rating rating = Rating.valueOf(parts[2].trim().toUpperCase());
                    Game game = new Game(title, year, rating);
                    addGame(game);
                }
            }
            return true;
        } catch (IOException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean loadOwnersFromFile(String fileName) {
        Path filePath = Paths.get("src", "user_files", fileName);
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    String gameTitle = parts[1].trim();
                    Vlastnik owner = findOrCreateOwner(name);
                    Game game = findGameByTitle(gameTitle);
                    if (game != null) {
                        owner.addGame(game);
                        game.addOwner(owner);
                    }
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private Vlastnik findOrCreateOwner(String name) {
        for (Vlastnik owner : owners) {
            if (owner.getName().equalsIgnoreCase(name)) {
                return owner;
            }
        }
        Vlastnik newOwner = new Vlastnik(name);
        addOwner(newOwner);
        return newOwner;
    }

    private Game findGameByTitle(String title) {
        for (Game game : games) {
            if (game.getTitle().equalsIgnoreCase(title)) {
                return game;
            }
        }
        return null;
    }

    public boolean loadGamesFromBinaryFile(String fileName) {
        Path filePath = Paths.get("src", "user_files", fileName);
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath.toFile()))) {
            games = (ArrayList<Game>) ois.readObject();
            return true;
        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
    }

    public boolean saveGamesToFile(String fileName) {
        Path filePath = Paths.get("src", "user_files", fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            for (Game game : games) {
                writer.write(String.format("%s, %d, %s%n",
                        game.getTitle(), game.getYear(), game.getRating()));
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean saveOwnersToBinaryFile(String fileName) {
        Path filePath = Paths.get("src", "user_files", fileName);
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath.toFile()))) {
            for (Vlastnik owner : owners) {
                writeOwnerToStream(bos, owner);
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void writeOwnerToStream(BufferedOutputStream bos, Vlastnik owner) throws IOException {
        byte[] nameBytes = owner.getName().getBytes();
        int gameCount = owner.getGames().size();

        bos.write(nameBytes.length); 
        bos.write(nameBytes); 
        bos.write(gameCount); 
    }

    public List<String> listOwnersWithGames() {
        List<String> results = new ArrayList<>();
        for (Vlastnik owner : owners) {
            for (Game game : owner.getGames()) {
                results.add(owner.getName() + " owns " + game.getTitle());
            }
        }
        return results;
    }

    public void sortGamesByYear() {
        games.sort(Comparator.comparingInt(Game::getYear));
    }

    public void sortOwnersByName() {
        owners.sort(Comparator.comparing(Vlastnik::getName));
    }
    
    private Vlastnik findOwnerByName(String name) {
        for (Vlastnik owner : owners) {
            if (owner.getName().equalsIgnoreCase(name)) {
                return owner;
            }
        }
        return null;
    }
    
    public String getOwnerGamesInfo(String ownerName) {
        Vlastnik owner = findOwnerByName(ownerName);
        if (owner == null) {
            return "Owner not found.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(ownerName).append(" owns ").append(owner.getGames().size()).append(" games: ");
        for (Game game : owner.getGames()) {
            sb.append(game.getTitle()).append(", ");
        }
        return sb.toString();
    }
    
     public String getGameOwnersInfo(String gameTitle) {
        Game game = findGameByTitle(gameTitle);
        if (game == null) {
            return "Game not found.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(gameTitle).append(" is owned by ").append(game.getOwners().size()).append(" people: ");
        for (Vlastnik owner : game.getOwners()) {
            sb.append(owner.getName()).append(", ");
        }
        return sb.toString();
    }
}


