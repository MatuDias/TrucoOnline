public class ClientTest
{
    public static void main(String[] args) {
        Deck deck = new Deck();
        Hand hand1 = new Hand();
        Hand hand2 = new Hand();

        hand2.startingHand(deck);
        hand1.startingHand(deck);

        System.out.println(hand1);
        System.out.println(hand2);


    }
}
