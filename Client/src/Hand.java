import java.lang.reflect.Array;
import java.util.ArrayList;

public class Hand extends Warning implements Cloneable
{
    private ArrayList<Card> hand;

    public Hand()//Deck deck (maybe)
    {

        hand = new ArrayList<>();
//        for(int i=0; i<3; i++)
//        {
//            hand.add(deck.getACard());
//        }

    }

    public void startingHand(Deck deck)
    {
        resetHand();

        for (int i = 0; i < 3; i++)
        {
            hand.add(deck.getACard());
        }
    }

    public ArrayList<Card> getHand() {
        return hand;
    }


    public void resetHand()
    {
        hand.clear();
    }

    public void recount(Card vira)
    {
        for(Card card: hand)
        {
            if(vira.getValue()+1 == card.getValue())
            {
                switch (card.getSymbol())
                {
                    case "Espadas" -> {}
                    case "Paus" -> {}
                    case "" -> {}
                }
            }
        }
    }

    @Override
    public String toString()
    {
        String ret = "mao = [";

        for (Card card: hand)
        {
            ret += " "+ card;
        }

        return ret + " ]";
    }

}
