//
//  main.cpp
//  Cards
//
//  Created by Brian Erichsen Fagundes on 8/29/23.
//

#include <iostream>
#include <string>
#include <vector>

using namespace std;


struct card {
    int rank; //here rank is from 0 to 12; 0 being the number Two and Ace 12;
    enum CardSuit {Spades, Hearts, Clubs, Diamonds} suit; //enum is a special data-type of constants, the card's suit in this case;
};
//const char* suitNames[] = {"Spades", "Hearts", "Clubs", "Diamonds"};
//const char* rankNames[] = {"Two", "Three", "Four", "Five", "Six", "Seven",
//    "Eight", "Nine", "Ten", "Joker", "Queen", "King", "Ace"};
// I wanted to use the above code but I know that we will still cover pointers;
// using two vectors to store the suitNames and rankNames, note how rank name index
// is same as their actual ranks;
vector<string> suitNames = {"Spades", "Hearts", "Clubs", "Diamonds"};
vector<string> rankNames = {"Two", "Three", "Four", "Five", "Six", "Seven",
        "Eight", "Nine", "Ten", "Joker", "Queen", "King", "Ace"};
// void so it does not return a value but it prints the content of a vector<card>;
void printCards (vector<card> a) {
    for (card b : a) {
        cout << "Card rank is " << rankNames[b.rank] << " and card suit is " <<
        suitNames[b.suit] << endl;
    }
}

int main() {
    vector<card> deck;
    
    const int numSuits = 4; // from 0 to 3 on the loop
    const int numRanks = 13;// from 0 to 12 on the loop;
    
    for (int rank = 0; rank < numRanks; ++rank) { //rank++ or ++rank
        for (int suit = 0; suit < numSuits; ++suit) {
            card newCard;// declaring new card will be added into deck vector;
            newCard.rank = rank;// rank loop will go from 0 to 12;
            newCard.suit = static_cast<card::CardSuit>(suit);//for each 0 to 12 rank
            // a suit will be declared into new card;
            deck.push_back(newCard); // adding new card with rank and suit into deck
        }
    }
    printCards(deck);
    
    return 0;
}
