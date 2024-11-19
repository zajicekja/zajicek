package datastore;

import java.util.Arrays;

/**
 * Contains data for cards
 */
public class DataStore {
    private static String gameName = "Eleven Game";
    
    private static int nTableCards = 9;
    
    private static String[] values
            = {"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};
    
    private static int[] points
            = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 0, 0, 0};

    private static String[] symbols
            = {"hearts", "clubs", "diamonds", "spades"};

    private static String[] triple = {"jack", "queen", "king"};
    
    
    public static String getGameName(){   
        return gameName;
    }
    
    public static int getNTableCards(){
        return nTableCards;
    }
    
    public static String[] getValues() {
        return Arrays.copyOf(values, values.length);
    }
    
    public static int[] getPoints(){
        return Arrays.copyOf(points, points.length);
    }
    
    public static String[] getSymbols() {
        return Arrays.copyOf(symbols, symbols.length);
    }
    
    public static String[] getTriple(){
        return Arrays.copyOf(triple, triple.length);
    }
}
