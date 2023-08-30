//
//  Cards.hpp
//  Cards
//
//  Created by Brian Erichsen Fagundes on 8/29/23.
//

#ifndef Cards_hpp
#define Cards_hpp

#include <stdio.h>
#include <string>
#include <vector>

struct card {
    int rank; //here rank is from 0 to 12; 0 being the number Two and Ace 12;
    enum CardSuit {Spades, Hearts, Clubs, Diamonds} suit; //enum is a special data-type of constants, the card's suit in this case;
};
std::vector<std::string> suitNames = {"Spades", "Hearts", "Clubs", "Diamonds"};
std::vector<std::string> rankNames = {"Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Joker", "Queen", "King", "Ace"};
void printCards (std::vector<card> a);
#endif /* Cards_hpp */
