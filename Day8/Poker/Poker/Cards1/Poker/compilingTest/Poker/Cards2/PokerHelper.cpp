//
//  PokerHelper.cpp
//  Cards2
//
//  Created by Brian Erichsen Fagundes on 8/31/23.
//

#include "PokerHelper.hpp"
#include <vector>
#include <string>
#include <iostream>
#include <random>
#include <algorithm>
#include <ctime>

using namespace std;
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
    mt19937 rng(time(0));// calling it's function to seconds;
    for (int b = 0; b <= a.size() - 1; b++) { //loops through each index of the vector;
        uniform_int_distribution<int> dist(b, 51);// b min and 51 for max for random number;
        int random = dist(rng); // int random by calling declared dist(distribution);
        swap(a[b], a[random]);// swaping numbers;
    }
}
bool isFlush (const vector<card>& a) {
    card::CardSuit targetSuit = a[0].suit; //assigns index 0 suit to new card.suit;
    for (card b : a) {
        if (b.suit != targetSuit) { //if same suit, returns true;
            return false; 
        }
    }
    return true;
}
bool isStraight (const vector<card>& hand) {
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
bool isStraightFlush (const vector<card>& hand) { //still testing this fucntion to be 100%;
    if (isStraight(hand) && isFlush(hand)) {
        return true;
    }
    return false;
}
bool isRoyalFlush (const vector<card>& hand) {
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
bool isAce (const vector<card>& hand) {
    //find match rank in other index that is not itself;
    card p1; p1.rank = hand[0].rank;
    int EqualCard = 0;
    for (int a = 0; a <= hand.size() - 1; ++a) {
        if (p1.rank == hand[a].rank) {
            EqualCard++;
        }
        if (EqualCard == 2) { //could be AcesNum >= 2 but once it finds at least two
            // equal cards it returns true since it still inside the loop;
            return true;
        }
    }
    return false;
}
bool isFullHouse (const vector<card>& hand) {
    card triple; triple.rank = hand[0].rank; //set card to be compared first to index 0 rank;
    int TripleCards = 0; //initialising total count of triples first to be 0;
    for (int a = 0; a <= hand.size() - 1; ++a) {
        if (triple.rank == hand[a].rank) {
            TripleCards++;
        }
    }
    if (TripleCards != 3) {
        return false; //returns false if there isn't a 3 cards of the same rank;
    }
    int pairRank = -1;//Initialising pairRank to be a non existing rank to be compared;
    for (card b : hand) {
        if (b.rank != triple.rank) {
            if (pairRank == - 1) { //The loops checks for card.rank that is different than
                // triple.rank and different from pairRank; first time card is found it is
                // assigned it to pairRank;
                // this block also ensures that loop only modifies pairRank once;
                pairRank = b.rank;
            } else if (pairRank != b.rank) {
                return false;
            }
        }
    }
    return true;
}
