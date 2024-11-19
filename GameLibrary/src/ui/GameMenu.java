package ui;

import java.util.Scanner;
import app.Library;
import app.Game;
import app.Rating;

public class GameMenu {
    public static void displayMenu(Scanner scanner, Library library) {
        boolean running = true;

        while (running) {
            System.out.println("Game Menu");
            System.out.println("1. Add Game");
            System.out.println("2. List Games");
            System.out.println("3. Back to Main Menu");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            switch (choice) {
                case 1:
                    addGame(scanner, library);
                    break;
                case 2:
                    listGames(library);
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addGame(Scanner scanner, Library library) {
        System.out.print("Enter game title: ");
        String title = scanner.nextLine();

        System.out.print("Enter release year: ");
        int year = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        System.out.print("Enter game rating (EXCELLENT, GOOD, AVERAGE, POOR): ");
        String ratingStr = scanner.nextLine().toUpperCase();
        Rating rating = Rating.valueOf(ratingStr);

        Game game = new Game(title, year, rating);
        library.addGame(game);
        System.out.println("Game added successfully.");
    }

    private static void listGames(Library library) {
        for (Game game : library.getGames()) {
            System.out.println(game);
        }
    }
}
