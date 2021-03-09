public class Card
{
    private final char name;
    private final String symbol;
    private int value;


    public Card(Character name, String symbol) throws Exception
    {
        if(name == null || symbol == null)
            throw new Exception("name || symbol eh nulo");

        this.name = name;
        this.symbol = symbol;

        switch (name)
        {
            case '4' -> value=1;
            case '5' -> value=2;
            case '6' -> value=3;
            case '7' -> value=4;
            case 'Q' -> value=5;
            case 'J' -> value=6;
            case 'K' -> value=7;
            case '2' -> value=8;
            case '3' -> value=9;
        }
    }


    @Override
    public String toString() {
        return name + "-" + symbol;
    }
}
