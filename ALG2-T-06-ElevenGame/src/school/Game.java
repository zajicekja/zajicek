package school;

import java.util.ArrayList;
import java.util.List;
import utils.GameInterface;

public class Game implements GameInterface {

    private Table table;
    private Deck deck;

    public Game() {
        this.deck = new Deck();
        this.table = new Table(deck.get9());
    }

    @Override
    public int getDeckSize() {
        return deck.getDeck().size();
    }

    @Override
    public String getCardDescriptionAt(int index) {
        Card c = table.getCard(index);
        return c.getSymbol() + "-" + c.getValue();
    }

    @Override
    public boolean isAnotherPlayPossible() {
        boolean eleven = false;
        boolean triple = false;

        ArrayList<Card> table = this.table.getTable();

        for (int i = 0; i < table.size() - 1; i++) {
            for (int j = i + 1; j < table.size(); j++) {
                if (table.get(i).getPoint() + table.get(j).getPoint() == 11) {
                    eleven = true;
                    break;
                }
            }
            if (eleven) {
                break;
            }
        }

        int n = 0;

        for (int i = 0; i < table.size(); i++) {
            if (table.get(i).getPoint() == 0) {
                n++;
            }
            if (n == 3) {
                triple = true;
                break;
            }
        }

        return eleven || triple;
    }

    @Override
    public boolean playAndReplace(List<Integer> iSelectedCards) {
        int sel1 = iSelectedCards.get(0);
        int sel2 = iSelectedCards.get(1);
        int sel3 = 0;

        int point1 = table.getCard(sel1).getPoint();
        int point2 = table.getCard(sel2).getPoint();
        int point3 = 0;

        if (iSelectedCards.size() > 2) {
            sel3 = iSelectedCards.get(2);
            point3 = table.getCard(sel3).getPoint();
        }

        boolean played = false;

        if ((point1 + point2 + point3) == 0 && iSelectedCards.size() > 2) { // IF TRIPLE THEN REMOVE ALL 3  
            table.getTable().remove(sel1);
            if (this.getDeckSize() > 0) {
                table.getTable().add(sel1, deck.getDeck().get(0));
                deck.getDeck().remove(0);
            }
            table.getTable().remove(sel2);
            if (this.getDeckSize() > 0) {
                table.getTable().add(sel2, deck.getDeck().get(0));
                deck.getDeck().remove(0);
            }
            table.getTable().remove(sel3);
            if (this.getDeckSize() > 0) {
                table.getTable().add(sel3, deck.getDeck().get(0));
                deck.getDeck().remove(0);
            }
            played = true;
        } else {
            if (point1 + point2 == 11) {        // ELSE CHECK CARD 1, 2
                table.getTable().remove(sel1);
                table.getTable().add(sel1, deck.getDeck().get(0));
                deck.getDeck().remove(0);

                table.getTable().remove(sel2);
                table.getTable().add(sel2, deck.getDeck().get(0));
                deck.getDeck().remove(0);
                played = true;
            }
        }

        return played;
    }

    @Override
    public boolean isWon() {
        return table.getTable().isEmpty() && this.getDeckSize() == 0;
    }

}
