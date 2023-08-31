//
//  main.cpp
//  Cards2
//
//  Created by Brian Erichsen Fagundes on 8/29/23.
//

#include <iostream>
#include <string>
#include <vector>
#include <ctime>
#include <cmath>
#include <random>
#include <algorithm>
using namespace std;


struct card {
    int rank; //here rank is from 0 to 12; 0 being the number Two and Ace 12;
    enum CardSuit {Spades, Hearts, Clubs, Diamonds} suit; //enum is a special data-type of constants, the card's suit in this case;
};
// using two vectors to store the suitNames and rankNames, note how rank name index
// is same as their actual ranks;
const vector<string> suitNames = {"Spades", "Hearts", "Clubs", "Diamonds"};
const vector<string> rankNames = {"Two", "Three", "Four", "Five", "Six", "Seven",
        "Eight", "Nine", "Ten", "Joker", "Queen", "King", "Ace"};
// void so it does not return a value but it prints the content of a vector<card>;
void printCards (vector<card> a) {
    for (card b : a) {
        cout << "Card rank is " << rankNames[b.rank] << " and card suit is " <<
        suitNames[b.suit] << endl;
//        cout << b.rank << endl; if you want to check cards rank;
    }
}
void shuffleDeck (vector<card>& a) {
    mt19937 rng(time(0)); //mt19937 is a better approach than srand(time(0));
    // time 0 so it generates a new random number each time instead of same num;
    for (int b = 0; b <= a.size() - 1; b++) {
        uniform_int_distribution<int> dist(b, 51); // appropriate range;
        int random = dist(rng); //generates new random number
        swap(a[b], a[random]); // swap indexes from original to random // note that
        // even with duplicates it still works because loop changes once each card;
    }
}
bool isFlush (vector<card> a) {
    card::CardSuit targetSuit = a[0].suit; //assigns index 0 suit to new card.suit;
    for (card b : a) {
        if (b.suit != targetSuit) { //if same suit, returns true;
            return false;
        }
    }
    return true;
}
bool isStraight (vector<card> hand) {
    vector<card> sortedHand = hand; // creates new vector = to original vector;
    // using sort syntax for struct vectors(beg, end, [] indexes comparison;
    sort(sortedHand.begin(), sortedHand.end(), [](const card& a,const card& b) {
        return a.rank < b.rank; // returns true if rank of a is less than rank of b;
        // syntax reference: https://en.cppreference.com/w/cpp/algorithm/sort
    });// 3rd example there contains struct example;
    for (int c = 0; c < hand.size() - 1; ++c) {
        if (sortedHand[c + 1].rank != sortedHand[c].rank + 1) {
            return false; // this statement is not the opposite or else I would
            // have a false positive on my numbers;
        }
    }
    return true; // else false;
}
bool isStraightFlush (vector<card> hand) { //still testing this fucntion to be 100%;
    if (isStraight(hand) && isFlush(hand)) {
        return true;
    }
    return false;
}
bool isRoyalFlush (vector<card> hand) {
    card min; min.rank = hand[0].rank; //I used my findMin function from previous
    for (card a : hand) {          //just had to change type of variable;
        if (a.rank < min.rank) {
            min = a;
        }
    } // card rank name 10 is same as int rank 8;
    if ((min.rank == 8) && (isStraightFlush(hand))) {
        return true;
    }
    return false;
}
bool isAce (vector<card> hand) {
    //find match rank in other index that is not itself;
    card p1; p1.rank = hand[0].rank;
    int EqualCard = 0;
    for (int a = 0; a <= hand.size() - 1; ++a) {
        if (p1.rank == hand[a].rank) {
            EqualCard++;
        }
        if (EqualCard == 2) { //could be AcesNum >= 2 but once it finds at least two
            // equal cards it returns true;
            return true;
        }
    }
    return false;
}
//bool isFullHouse (vector<card> hand) {
//    
//    return false;
//}

int main() {
    vector<card> deck; // composes the deck of cards;
    
    const int numSuits = 4; // from 0 to 3 on the loop
    const int numRanks = 13;// from 0 to 12 on the loop;
    
    for (int rank = 0; rank < numRanks; ++rank) {
        for (int suit = 0; suit < numSuits; ++suit) {
            card newCard;// declaring new card will be added into deck vector;
            newCard.rank = rank;// rank loop will go from 0 to 12;
            newCard.suit = static_cast<card::CardSuit>(suit);//for each 0 to 12 rank
            // a suit will be declared into new card;
            deck.push_back(newCard); // adding new card with rank and suit into deck
        }
    }
    shuffleDeck(deck); // shuffles deck;
    // takes the last 5 cards of shuffled deck to be the player's hand;
    vector<card> hand = {deck[51], deck[50], deck[49], deck[48], deck[47]};
    printCards(hand);
    
    
    card s1; s1.rank = 8;
    card s2; s2.rank = 9;
    card s3; s3.rank = 10;
    card s4; s4.rank = 11;
    card s5; s5.rank = 12;
    
    s5.card::suit = card::Clubs;
    s4.card::suit = card::Clubs;
    s3.card::suit = card::Clubs;
    s2.card::suit = card::Clubs;
    s1.card::suit = card::Clubs;
    
    card s6; s6.rank = 8; s6.card::suit =card::Spades;
    
    vector<card> test = {s1, s2, s3, s4, s5};
    cout << isStraight(test) << endl;
    cout << isStraightFlush(test) << endl;
    cout << isRoyalFlush(test) << endl;
    cout << isAce(test) << endl;
    
    return 0;
}

