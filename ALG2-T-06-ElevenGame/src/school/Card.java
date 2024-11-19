package school;

public class Card {
    private String value;
    private int point;
    private String symbol;
    
    public Card(String value, int point, String symbol){
        this.value = value;
        this.point = point;
        this.symbol = symbol;
    }

    public String getValue() {
        return value;
    }

    public int getPoint() {
        return point;
    }

    public String getSymbol() {
        return symbol;
    }
}