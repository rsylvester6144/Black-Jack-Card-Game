import java.util.*;
public class blackjack {
    
    public class Dealer{
        private int[] playerCards = new int [10];
        private int[] dealerCards = new int [10];
        private ArrayList<String> deck = new ArrayList<String>(); 

        public void createDeck(){
            // Add 52 cards to the deck
            for (int i = 0; i < 4; i++){
                deck.add("2");
                deck.add("3");
                deck.add("4");
                deck.add("5");
                deck.add("6");
                deck.add("7");
                deck.add("8");
                deck.add("9");
                deck.add("10");
                deck.add("J");
                deck.add("Q");
                deck.add("K");
                deck.add("A");
            }

            // Simulate a shuffle of the deck
            Collections.shuffle(deck);
        }

        public void initialDeal(){
            playerCards[0] = drawCard();
            dealerCards[0] = drawCard();
            playerCards[1] = drawCard();
            dealerCards[1] = drawCard();
        }

        public int drawCard(){
            String card = deck.remove(0);
            return parseCardValue(card);
        }
        private int parseCardValue(String card) {
            switch (card) {
                case "J":
                case "Q":
                case "K":
                    return 10;
                case "A":
                    return 11;
                default:
                    return Integer.parseInt(card);
            }
        }

        public int[] getHandValue(int[] hand) {
            int total = 0;
            int aceCount = 0;

            for(int card : hand){
                if (card == 0) {
                    break; // No more cards in hand
                }

                if(card == 11){
                    aceCount++;
                    total += 1;
                }
                else{
                    total += card;
                }
            }

            // alternative total if aces are counted as 11
            int altTotal = total;
            if (aceCount > 0 && altTotal + 10 <= 21) {
                altTotal += 10;
            }

            if (altTotal != total) {
                return new int[]{total, altTotal};
            } else {
                return new int[]{total};
            }
        }


        public void printHandsFormatted(boolean hideDealerCard) {
            System.out.println("------------------------------------------------------------");
            System.out.printf("%-30s%s%n", "Player", "Dealer");
            System.out.println("------------------------------------------------------------");

            String playerHandStr = buildHandString(playerCards, false, false);
            String dealerHandStr = buildHandString(dealerCards, hideDealerCard, true);

            System.out.printf("%-30s%s%n", playerHandStr, dealerHandStr);

            String playerTotalStr = formatHandTotal(playerCards, false);
            String dealerTotalStr = formatHandTotal(dealerCards, hideDealerCard);

            System.out.printf("Total: %-24sTotal: %s%n", playerTotalStr, dealerTotalStr);
            System.out.println("------------------------------------------------------------");
        }

        private String buildHandString(int[] hand, boolean hideSecondCard, boolean isDealer) {
            StringBuilder handStr = new StringBuilder();
            for (int i = 0; i < hand.length; i++) {
                if (hand[i] == 0) break;
                if (isDealer && i == 1 && hideSecondCard) {
                    handStr.append("?  ");
                } else {
                    handStr.append(hand[i]).append("  ");
                }
            }
            return handStr.toString();
        }

        private String formatHandTotal(int[] hand, boolean hideTotal) {
            if (hideTotal) return "?";
            int[] totals = getHandValue(hand);
            return (totals.length == 2)
                ? totals[0] + " / " + totals[1]
                : String.valueOf(totals[0]);
        }

        public boolean isBust(int[] hand) {
            int total = getHandValue(hand)[0];
            return total > 21;
        }

        public boolean isBlackjack(int[] hand) {
            int[] values = getHandValue(hand);
            for (int value : values) {
                if (value == 21) {
                    return true;
                }
            }
            return false;
        }

        public void dealerPlay() {
            while (true) {
                int[] values = getHandValue(dealerCards);
                int bestValue = values[values.length - 1]; // Get the highest valid value

                if (bestValue < 17) {
                    dealerCards[nextEmptyIndex(dealerCards)] = drawCard();
                } else {
                    break;
                }
            }
        }
        private int nextEmptyIndex(int[] hand) {
            for (int i = 0; i < hand.length; i++) {
                if (hand[i] == 0) {
                    return i;
                }
            }
            return hand.length;
        }

        public String determineWinner() {
            int[] playerValues = getHandValue(playerCards);
            int[] dealerValues = getHandValue(dealerCards);

            int playerBest = playerValues[playerValues.length - 1];
            int dealerBest = dealerValues[dealerValues.length - 1];

            if (isBust(playerCards)) {
                return "Dealer wins! Player busted.";
            } else if (isBust(dealerCards)) {
                return "Player wins! Dealer busted.";
            } else if (playerBest > dealerBest) {
                return "Player wins!";
            } else if (dealerBest > playerBest) {
                return "Dealer wins!";
            } else {
                return "It's a tie!";
            }
        }

        public void playerHit() {
            playerCards[nextEmptyIndex(playerCards)] = drawCard();
        }

    }
    
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Dealer dealer = new blackjack().new Dealer();

        System.out.println("Welcome to Blackjack!");
        
        // Prepare deck and deal initial hands
        dealer.createDeck();
        dealer.initialDeal();

        // Check for initial Blackjack
        boolean playerBlackjack = dealer.isBlackjack(dealer.playerCards);
        boolean dealerBlackjack = dealer.isBlackjack(dealer.dealerCards);

        System.out.println("\nInitial hands:");
        dealer.printHandsFormatted(true); // hide dealer’s second card

        if (playerBlackjack || dealerBlackjack) {
            // Reveal both hands for the final result
            System.out.println("\nFinal Hands:");
            dealer.printHandsFormatted(false);

            if (playerBlackjack && dealerBlackjack) {
                System.out.println("\nBoth player and dealer have Blackjack! It's a tie!");
            } else if (playerBlackjack) {
                System.out.println("\nPlayer has Blackjack! Player wins!");
            } else {
                System.out.println("\nDealer has Blackjack! Dealer wins!");
            }
            input.close();
            return; // Stop the game
        }

        // Player's turn
        boolean playerTurn = true;
        boolean firstRound = true;
        while (playerTurn) {
            if (!firstRound) {
                dealer.printHandsFormatted(true); // only print again after first decision
            } else {
                firstRound = false; // skip reprinting 
        }

            // Ask for action
            System.out.println("Do you want to Hit or Stand? (H/S)");
            String choice = input.nextLine().toUpperCase();

            if (choice.equals("H")) {
                dealer.playerHit();
                // Check if player busted
                if (dealer.isBust(dealer.playerCards)) {
                    dealer.printHandsFormatted(true);
                    System.out.println("You busted!");
                    playerTurn = false;
                }
            } else if (choice.equals("S")) {
                playerTurn = false;
            } else {
                System.out.println("Invalid choice. Enter H or S.");
            }
        }

        // Dealer's turn
        dealer.dealerPlay();

        // Show final hands and totals
        System.out.println("\nFinal Hands:");
        dealer.printHandsFormatted(false);

        // Determine winner
        System.out.println("\n" + dealer.determineWinner());

        input.close();
    }




}