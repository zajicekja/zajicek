package ui;

import java.util.Scanner;
import app.Library;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();
        boolean running = true;

        while (running) {
            System.out.println("Main Menu");
            System.out.println("1. Manage Games");
            System.out.println("2. Load Games from Text File");
            System.out.println("3. Load Games from Binary File");
            System.out.println("4. Save Games to Text File");
            System.out.println("5. Save Owners to Binary File");
            System.out.println("6. Load Owners from Text File");
            System.out.println("7. List Owners with Games");
            System.out.println("8. Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            switch (choice) {
                case 1:
                    GameMenu.displayMenu(scanner, library);
                    break;
                case 2:
                    System.out.print("Enter the name of the text file: ");
                    String textFileName = scanner.nextLine();
                    if (library.loadGamesFromFile(textFileName)) {
                        System.out.println("Games loaded successfully from text file.");
                    } else {
                        System.out.println("Error reading text file.");
                    }
                    break;
                case 3:
                    System.out.print("Enter the name of the binary file: ");
                    String binaryFileName = scanner.nextLine();
                    if (library.loadGamesFromBinaryFile(binaryFileName)) {
                        System.out.println("Games loaded successfully from binary file.");
                    } else {
                        System.out.println("Error reading binary file.");
                    }
                    break;
                case 4:
                    System.out.print("Enter the name of the text file: ");
                    String saveTextFileName = scanner.nextLine();
                    if (library.saveGamesToFile(saveTextFileName)) {
                        System.out.println("Games saved successfully to text file.");
                    } else {
                        System.out.println("Error writing to text file.");
                    }
                    break;
                case 5:
                    System.out.print("Enter the name of the binary file: ");
                    String saveOwnersBinaryFileName = scanner.nextLine();
                    if (library.saveOwnersToBinaryFile(saveOwnersBinaryFileName)) {
                        System.out.println("Owners saved successfully to binary file.");
                    } else {
                        System.out.println("Error writing to binary file.");
                    }
                    break;
                case 6:
                    System.out.print("Enter the name of the owners text file: ");
                    String ownersFileName = scanner.nextLine();
                    if (library.loadOwnersFromFile(ownersFileName)) {
                        System.out.println("Owners loaded successfully from text file.");
                    } else {
                        System.out.println("Error reading owners text file.");
                    }
                    break;
                case 7:
                    for (String entry : library.listOwnersWithGames()) {
                        System.out.println(entry);
                    }
                    break;
                case 8:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }
}
