//
//  main.cpp
//  DiyVector
//
//  Created by Brian Erichsen Fagundes & Mina on 9/12/23.
// This program makes my own class; vector;

#include <iostream>
#include <string>
#include "MyVector.hpp"
using namespace std;
    
int main(int argc, const char* argv[]) {
    testMyVector();
   
    MyVector<int> t;
    t.push_back(2);
    t.push_back(3);
    for (int i : t) {
        cout << i << " ";
    }
    
    return 0;
}
