//
//  main.cpp
//  NumberConverter
// Created by Brian Erichsen Fagundes &  Mina Akbari;
//  Created by Brian Erichsen Fagundes on 9/5/23.
//

#include <iostream>
#include <string>
#include <cctype>
#include <cassert>
#include <vector>
#include <math.h>
#include <cmath>
#include "ConverterFunctions.hpp"


int main(int argc, const char * argv[]) {
    using namespace std;
    string digits;
    int base;
    cout << "Enter a string of digits & and it's base: ";
    cin >> digits >> base;
    cout << stringToInt(digits, base) << endl;
    cout << intToDecimalString(10) << endl;
    cout << intToBinaryString(77777) << endl;
    cout << intToHexadecimalString(999) << endl;
    return 0;
}
