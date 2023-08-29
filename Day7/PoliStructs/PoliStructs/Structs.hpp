//
//  Structs.hpp
//  PoliStructs
//
//  Created by Brian Erichsen Fagundes on 8/29/23.
//

#ifndef Structs_hpp
#define Structs_hpp
#include <vector>
#include <string>
#include <stdio.h>

using namespace std;
struct Politician {
    string name;
    string party;
    string state_federal;
};
vector<Politician> Javacans (vector<Politician> a);
vector<Politician> federalCpLusers (vector<Politician> a);
#endif /* Structs_hpp */
