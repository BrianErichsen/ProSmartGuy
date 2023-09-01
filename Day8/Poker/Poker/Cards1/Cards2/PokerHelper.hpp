//
//  PokerHelper.hpp
//  Cards2
//
//  Created by Brian Erichsen Fagundes on 8/31/23.
//

#ifndef PokerHelper_hpp
#define PokerHelper_hpp
#include <vector>
#include <random>
#include <stdio.h>

using namespace std;
struct card {
    int rank; //here rank is from 0 to 12; 0 being the number Two and Ace 12;
    enum CardSuit {Spades, Hearts, Clubs, Diamonds} suit; //enum is a special data-type of constants, the card's suit in this case;
};
void printCards (vector<card> a);
void shuffleDeck (vector<card>& a);
bool isFlush (const vector<card>& a);
bool isStraight (const vector<card>& hand);
bool isStraightFlush (const vector<card>& hand);
bool isRoyalFlush (const vector<card>& hand);
bool isAce (const vector<card>& hand);
bool isFullHouse (const vector<card>& hand);
#endif /* PokerHelper_hpp */
