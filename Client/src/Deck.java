import java.util.ArrayList;

public class Deck
{
    private ArrayList<Card> deck;

    public Deck()
    {
        deck = new ArrayList<>();

        for (int i = 0; i < CardsProperty.names.length; i++)
        {
            for (int j = 0; j < CardsProperty.symbols.length; j++)
            {
                try
                {
                    deck.add(new Card(CardsProperty.names[i], CardsProperty.symbols[j]));
                }
                catch (Exception ignored){}
            }
        }
    }

    public Card getACard()
    {
        int index = (int) (Math.random() * deck.size());
        Card ret = deck.get(index); // copying the card from the array
        deck.remove(index); // deleting the card from the array

        return ret;
    }

}
