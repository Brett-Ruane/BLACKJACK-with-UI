import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class BlackJack {

    public static void play(Deck deck) {
        System.out.print("What's your name? ");
        Scanner stdIn = new Scanner(System.in);
        String name = stdIn.nextLine();
        System.out.println("Welcome " + name);
        Player one = new Player(name, 1);
        Player dealer = new Player("", 0);
        List<Card> oneCards = new ArrayList<Card>();
        List<Card> dealerCards = new ArrayList<Card>();

        Card dealt = deck.deal();
        oneCards.add(dealt);
        if (dealt.pointValue() == 11)
            one.addAce();
        one.add(dealt.pointValue());
        dealt = deck.deal();
        dealerCards.add(dealt);
        if (dealt.pointValue() == 11)
            dealer.addAce();
        dealer.add(dealt.pointValue());
        dealt = deck.deal();
        oneCards.add(dealt);
        if (dealt.pointValue() == 11)
            one.addAce();
        one.add(dealt.pointValue());
        dealt = deck.deal();
        dealerCards.add(dealt);
        if (dealt.pointValue() == 11)
            dealer.addAce();
        dealer.add(dealt.pointValue());

        printCards(oneCards);
        printDealerCards(dealerCards);

        String s = stdIn.nextLine();
        if (one.total() != 21) { // if not natural 21
            while (!s.equalsIgnoreCase("stand")) {
                if (s.equalsIgnoreCase("hit")) {
                    System.out.println("HIT");
                    dealt = deck.deal();
                    oneCards.add(dealt);
                    if (dealt.pointValue() == 11)
                        one.addAce();
                    one.add(dealt.pointValue());
                    printCards(oneCards);
                } else {
                    System.out.println("INVALID: TYPE AGAIN");
                }
                if (one.total() > 21 && one.getAce() > 0) {
                    one.subTotal(10);
                    one.subAce();
                } else if (one.total() > 21) {
                    System.out.println("Your Total is " + one.total());
                    System.out.println("BUST");
                    System.out.println("YOU LOOSE");
                    System.exit(1);
                }
                System.out.println("TOTAL = " + one.total());
                s = stdIn.nextLine();
            }
        } else if (one.total() == 21 && dealer.total() != 21) {
            System.out.println("YOU WIN");
        } else {
            System.out.println("TIE, Take Back Chips");
        }

        System.out.println("Your Total is " + one.total());

        while ((dealer.total() < 17) || (dealer.total() > 17 && dealer.getAce() != 0)) {
            if (dealer.total() > 17 && dealer.getAce() > 0) {
                dealer.subTotal(10);
                dealer.subAce();
            }
            dealt = deck.deal();
            dealerCards.add(dealt);
            dealer.add(dealt.pointValue());
            if (dealt.pointValue() == 11)
                dealer.addAce();
            dealer.add(dealt.pointValue());
            printDealerCards(dealerCards);
        }
        if (one.total() > dealer.total()) {
            System.out.println("YOU WIN");
        } else if (one.total() == dealer.total()) {
            System.out.println("TIE, Take Back Chips");
        } else {
            System.out.println("YOU LOOSE");
        }
        // System.out.println(deck);
        stdIn.close();
    }

    public static void printCards(List<Card> c) {
        System.out.print("Your Cards Are ");
        for (int i = 0; i < c.size(); i++)
            System.out.print(c.get(i) + ",");
        System.out.println("");
    }

    public static void printDealerCards(List<Card> c) {
        System.out.print("Dealers Cards Are ");
        for (int i = 0; i < c.size(); i++)
            System.out.print(c.get(i) + ",");
        System.out.println("");
    }

}