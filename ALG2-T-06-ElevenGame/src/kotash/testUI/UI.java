package kotash.testUI;

import datastore.DataStore;
import java.util.*;
import school.Game;
import utils.GameInterface;


public class UI {
    public static Scanner sc = new Scanner(System.in);

    static GameInterface game;
    
    public static void runGame() {
        startGame();

        game = new Game();

        while (!game.isWon() && game.isAnotherPlayPossible()) {
            printTable(generateTable(getCards()));
            game.playAndReplace(Arrays.asList(takeChoice()));
        }

        if (game.isWon()) winner();
        else loser();

        endGame();
    }

    private static void startGame() {
        System.out.println(Colors.BRIGHT_BG_GREEN + Colors.BLACK + " Welcome to ELEVEN GAME " + Colors.RESET_COLOR);
        System.out.println();
    }

    private static Card[] getCards() {
        int size = DataStore.getNTableCards();
        Card[] cards = new Card[size];

        for (int i = 0; i < size; i++) {
            String[] cardInfo = game.getCardDescriptionAt(i).split("-");
            cards[i] = new Card(cardInfo[0], cardInfo[1]);
        }

        return cards;
    }

    private static String generateTable(Card[] cards) {
        StringBuilder table = new StringBuilder();
        String spacer = "  ";

        // formatovani karet
        for (int i = 0; i < 4; i++) { // 4 - height of a card
            for (Card card : cards) {
                table.append(card.toString().split("\n")[i]).append(spacer);
            }
            table.append("\n");
        }
        
        // tisk indexu karet
        for (int i = 0; i < cards.length; i++) {
            table.append(Colors.RESET_COLOR);
            table.append(String.format(" %s%d%s ", spacer, i+1, spacer));
            table.append(spacer);
        }
        table.append("\n");

        return table.toString();
    }

    private static void printTable(String table) {
        System.out.println("Choose cards:");
        System.out.println(table);
        System.out.println(Colors.RESET_COLOR);
    }

    private static Integer[] takeChoice() {
        Integer[] answer;
        String[] input = sc.nextLine().split(" ");

        if (input.length < 2) throw new IllegalArgumentException("At least 2 cards");

        if (input.length == 2) {
            answer = new Integer[2];
        } else {
            answer = new Integer[3];
        }

        for (int i = 0; i < answer.length; i++) {
            answer[i] = Integer.parseInt(input[i]) - 1;
        }

        return answer;
    }

    private static void winner() {
        System.out.println();
        System.out.println(Colors.BRIGHT_BG_YELLOW + Colors.BLACK + " You are winner!!! " + Colors.RESET_COLOR);
        System.out.println();
    }

    private static void loser() {
        System.out.println();
        System.out.println(Colors.BRIGHT_BG_RED + Colors.BLACK + " You are loser!!! " + Colors.RESET_COLOR);
        System.out.println();
    }

    private static void endGame() {
        System.out.println();
        System.out.println(Colors.BRIGHT_BG_GREEN + Colors.BLACK + " We are waiting for you again! " + Colors.RESET_COLOR);
        System.out.println();
    }
}
