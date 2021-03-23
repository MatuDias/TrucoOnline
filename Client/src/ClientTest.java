public class ClientTest
{
    public static void main(String[] args) {
        Deck deck = new Deck();
        Hand hand1 = new Hand();
        Hand hand2 = new Hand();

        System.out.println("a:"+deck);


        hand2.startingHand(deck);
        hand1.startingHand(deck);



        System.out.println("b:"+deck);
        deck.resetDeck();
        System.out.println("c:"+deck);

        System.out.println(hand1);
        System.out.println(hand2);
    }
}
