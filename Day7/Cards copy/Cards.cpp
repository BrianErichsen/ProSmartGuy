//
//  Cards.cpp
//  Cards
//
//  Created by Brian Erichsen Fagundes on 8/29/23.
//

#include "Cards.hpp"
#include <vector>
#include <iostream>

using namespace std;

void printCards (vector<card> a) {
    for (card b : a) {
        cout << "Card rank is " << rankNames[b.rank] << " and card suit is " <<
        suitNames[b.suit] << endl;
    }
}
