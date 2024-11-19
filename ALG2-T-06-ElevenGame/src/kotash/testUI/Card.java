package kotash.testUI;

public class Card {
    String label;
    String value;
    boolean red;

    public Card(String label, String value) {
        this.label = convertLabels(label);
        this.value = convertValues(value);
        this.red = label.equals("diamonds") || label.equals("hearts");
    }

    private String convertLabels(String labelName) {
        switch (labelName) {
            case "diamonds":
                return "\u2666";
            case "clubs":
                return "\u2663";
            case "spades":
                return "\u2660";
            default:
                return "\u2665";
        }
    }

    private String convertValues(String value) {
        if (value.length() > 2) {
            return Character.toString(value.toUpperCase().toCharArray()[0]);
        }
        return value;
    }

    @Override
    public String toString() {
        String color = getColor(this.red);
        return String.format(
                        color + "%c%c%c%c%c%c%c\n" + resetColor() +
                        color + "%c%s%s%s%s%s%c\n" + resetColor() +
                        color + "%c%s%s%s%s%s%c\n" + resetColor() +
                        color + "%c%c%c%c%c%c%c\n" + resetColor(),
                ExtendedAscii.getAscii(200),
                ExtendedAscii.getAscii(204),
                ExtendedAscii.getAscii(204),
                ExtendedAscii.getAscii(204),
                ExtendedAscii.getAscii(204),
                ExtendedAscii.getAscii(204),
                ExtendedAscii.getAscii(186),

                ExtendedAscii.getAscii(185),
                " ",
                " ",
                value,
                value.length() > 1 ? "" : " ",
                " ",
                ExtendedAscii.getAscii(185),

                ExtendedAscii.getAscii(185),
                " ",
                " ",
                label,
                " ",
                " ",
                ExtendedAscii.getAscii(185),

                ExtendedAscii.getAscii(199),
                ExtendedAscii.getAscii(204),
                ExtendedAscii.getAscii(204),
                ExtendedAscii.getAscii(204),
                ExtendedAscii.getAscii(204),
                ExtendedAscii.getAscii(204),
                ExtendedAscii.getAscii(187)
        );
    }

    private String getColor(boolean red) {
        return red ? Colors.RED : Colors.BLUE;
    }

    private String resetColor() {
        return Colors.RESET_COLOR;
    }
}
