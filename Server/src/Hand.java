import java.util.ArrayList;

public class Hand extends Warning implements Cloneable
{
    ArrayList<Card> hand;

    public Hand(Deck deck)
    {

        hand = new ArrayList<>();
//        for(int i=0; i<3; i++)
//        {
//            hand.add(deck.getACard());
//        }

    }

    public ArrayList<Card> getHand() {
        return hand;
    }
}
