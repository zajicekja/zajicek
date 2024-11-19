package school;

import datastore.DataStore;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    ArrayList<Card> deck = new ArrayList();

    public Deck() {
        int[] points = DataStore.getPoints();
        String[] values = DataStore.getValues();
        String[] symbols = DataStore.getSymbols();

        for (int i = 0; i < symbols.length; i++) {
            for (int j = 0; j < points.length; j++) {
                deck.add(new Card(values[j], points[j], symbols[i]));
            }
        }

        shuffleDeck();
    }

    public ArrayList<Card> get9() {
        ArrayList<Card> cards = new ArrayList();
        for (int i = 0; i < 9; i++) {
            cards.add(deck.get(0));
            deck.remove(deck.get(0));
        }
        return cards;
    }

    private void shuffleDeck() {
        Collections.shuffle(deck);
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }
    
}