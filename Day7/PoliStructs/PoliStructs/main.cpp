//
//  main.cpp
//  PoliStructs
//
//  Created by Brian Erichsen Fagundes on 8/28/23.
//

#include <iostream>
#include <vector>
#include <string>
#include "Structs.hpp"
using namespace std;


int main() {
    Politician s1 = {"Bob", "Javacans", "federal"};
    Politician s2 = {"Tom", "Cplusers", "state"};
    Politician s3 = {"Cat", "Javacans", "state"};
    Politician s4 = {"Phak", "Cplusers", "federal"};
    
    vector<Politician> politicians = {s1, s2, s3, s4};
    vector<Politician> Java = Javacans(politicians);
    for (Politician a : Java) {
        cout << a.name << " " << endl;
    }
    vector<Politician> FederalCp = federalCpLusers(politicians);
    for (Politician a : FederalCp) {
        cout << a.name << " " << endl;
    }

    return 0;
}
