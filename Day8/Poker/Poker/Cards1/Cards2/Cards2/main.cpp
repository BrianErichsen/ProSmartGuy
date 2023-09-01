//
//  main.cpp
//  Cards2
//
//  Created by Brian Erichsen Fagundes on 8/29/23.
// Percentage of each hand ~~
// Flush - 12684 / ten million (7 zeros) and 24210 / ten million /0.12684% and 0.2421%
// Straight - 32626 / ten million and 33935 / ten million / 0.32626% and 0.33935%
// Straight Flush - 121 / ten million and 691 / ten million 0.00121% and 0.00691%;
// Royal Flush - 0 / ten million and 563 / ten million 0 % and 0.00563%;
// Full House - 6273 / ten million and 8993 / ten million 0.06273% and 0.08993;



#include <iostream>
#include "PokerHelper.hpp"
using namespace std;

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
    // takes the last 5 cards of shuffled deck to be the player's hand;
    vector<card> hand = {deck[51], deck[50], deck[49], deck[48], deck[47]};
    //Initialising all total of bool statments to be 0 initially;
    int totalFlush = 0;
    int totalStraight = 0;
    int totalStraightFlush = 0;
    int totalRoyalFlush = 0;
    int totalFullHouse = 0;

//        cout << isFlush(hand) << endl;
//        cout << isStraight(hand) << endl;
//        cout << isStraightFlush(hand) << endl;
//        cout << isRoyalFlush(hand) << endl;
//        cout << isAce(hand) << endl;
//        cout << isFullHouse(hand) << endl;
    
    for (int a = 0; a <= 10000000; ++a) {
        shuffleDeck(deck);
        hand = {deck[51], deck[50], deck[49], deck[48], deck[47]};
        if (isFlush(hand)) {
            totalFlush++;
        }
        if (isStraight(hand)) {
            totalStraight++;
        }
        if (isStraightFlush(hand)) {
            totalStraightFlush++;
        }
        if (isRoyalFlush(hand)) {
            totalRoyalFlush++;
        }
        if (isFullHouse(hand)) {
            totalFullHouse++;
        }
    }
    double fractionStraightFlush = totalStraightFlush / 10000000;
    cout << totalFlush << endl;
    cout << totalStraight << endl;
    cout << totalStraightFlush << endl;
    cout << totalRoyalFlush << endl;
    cout << totalFullHouse << endl;
    cout << "Fraction of Straight Flush in a given hand out of 10 Million times is: " <<
    fractionStraightFlush << endl;
    
    
//    card s1; s1.rank = 11;
//    card s2; s2.rank = 11;
//    card s3; s3.rank = 11;
//    card s4; s4.rank = 12;
//    card s5; s5.rank = 12;
//
//    s5.card::suit = card::Clubs;
//    s4.card::suit = card::Clubs;
//    s3.card::suit = card::Clubs;
//    s2.card::suit = card::Clubs;
//    s1.card::suit = card::Clubs;
//
//    card s6; s6.rank = 1; s6.card::suit =card::Spades;
//
//    vector<card> test = {s1, s2, s3, s4, s5};
//    cout << isFlush(test) << endl;
//    cout << isStraight(test) << endl;
//    cout << isStraightFlush(test) << endl;
//    cout << isRoyalFlush(test) << endl;
//    cout << isAce(test) << endl;
//    cout << isFullHouse(test) << endl;
    
    return 0;
}

