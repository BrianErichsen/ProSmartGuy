//
//  Structs.cpp
//  PoliStructs
//
//  Created by Brian Erichsen Fagundes on 8/29/23.
//

#include "Structs.hpp"
#include <vector>


vector<Politician> Javacans (vector<Politician> a) {
    vector<Politician> c;
    for (Politician b : a) {
        if (b.party == "Javacans") {
        c.push_back(b);
        }
    }
    return c;
}
vector<Politician> federalCpLusers (vector<Politician> a) {
    vector<Politician> c;
    for (Politician b : a) {
        if (b.party == "Cplusers" && b.state_federal == "federal") {
            c.push_back(b);
        }
    }
    return c;
}
