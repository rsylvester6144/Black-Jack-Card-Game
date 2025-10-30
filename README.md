# Black-Jack-Card-Game
A Java-based Blackjack simulator built to reinforce core software engineering principles such as encapsulation, modularity, and scalability. The project demonstrates an object-oriented approach to game design, where functionality is cleanly separated into components for clarity, maintainability, and future extensibility.

# Project Overview
This project simulates a simplified version of the popular card game Blackjack (also known as 21). Players compete against a dealer by drawing cards in an attempt to reach a total card value as close to 21 as possible without going over.

The program models real Blackjack mechanics including:
- Deck creation and shuffling
- Initial card dealing
- Player hit/stand logic
- Dealer play rules (must hit on <17)
- Hand value calculations (with Ace flexibility as 1 or 11)
- Blackjack and bust detection
- Clear formatted display of both hands

# Key Features
- Object-oriented design using encapsulation
- Realistic deck creation and shuffle simulation
- Flexible Ace handling (1 or 11 depending on hand value)
- Auto dealer play according to Blackjack rules
- Dynamic hand display with hidden dealer card
- Bust, Blackjack, and tie condition handling

# How to run
Requirements
- Java 8 or higher
- Command-line or IDE

Steps
1. Clone the repository: 
git clone https://github.com/rsylvester6144/Black-Jack-Card-Game.git
cd Black-Jack-Card-Game
2. Compile the program:
javac blackjack.java
3. Run the program:
Java blackjack

# Code structure
blackjack.java
│
├── public class blackjack
│   ├── Dealer class
│   │   ├── createDeck() — builds and shuffles deck
│   │   ├── initialDeal() — deals opening cards
│   │   ├── drawCard() — handles card drawing
│   │   ├── getHandValue() — computes totals, accounts for Aces
│   │   ├── printHandsFormatted() — displays player/dealer hands
│   │   ├── dealerPlay() — dealer logic
│   │   ├── determineWinner() — compares final results
│   │   └── playerHit() — adds card to player’s hand
│   └── main() — runs the game loop (hit/stand decisions, results)

# Author
Ryan Sylvester
The College of New Jersey -- Computer Science
https://github.com/rsylvester6144


