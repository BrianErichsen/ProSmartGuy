//
//  main.cpp
//  RomanNumerals
//
//  Created by Brian Erichsen Fagundes on 8/24/23.
//

#include <iostream>
#include <string>

using namespace std;

int main() {
    int number;
    // This loop repeats if input is negative;
    do {
        cout << "Enter a decimal number: ";
        cin >> number;
    }
    while (number < 0);
    // using 2 different arrays where first stores the values for roman numerals
    // and next one stores the roman symbols where each index corresponds to its value;
    int romanValues[] = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    string romanSymbols[] = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X",
        "IX", "V", "IV", "I"};
    
    string romanNumeral = ""; // starting with an empty string so that the loop
    // may add appropriate indexes into the string;
    
    // Using a nested loop; 'a' will be the index of both RomanValues and RomanSymbols,
    // where each time the loop adds current 'a' index to string romanNumeral & subtracts
    // index 'a' from number;
    for (int a = 0; number > 0; a++) {
        while (number >= romanValues[a]) { //while condition keeps a index to appropriate
            // number value or else does not enter loop hence giving appropriate answer;
            romanNumeral += romanSymbols[a];
            number -= romanValues[a];
        }
    }
    cout << "Roman numeral version: " << romanNumeral << endl;
    
    return 0;
}
