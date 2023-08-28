//
//  LetterHelpers.cpp
//  StringAnalyzer
//
//  Created by Brian Erichsen Fagundes on 8/28/23.
//

#include "LetterHelpers.hpp"
bool isVowel (char input) { // a, i, u, e, o y vowels, if char == vowels bool returns true;
    input = tolower(input); // only use tolower() once instead of multiple times;
    if (input == 'a' || input == 'e' ||
        input == 'i' || input == 'o' ||
        input == 'u' || input == 'y') {
        return true;
    }
    return false; // else returns false;
} // using same format for numVowels function; using bool isVowel to add number to count;
int numVowels (std::string input) {
    int number_of_vowels = 0;
    for (int a = 0; a <= input.length() - 1; a++) {
        if (isVowel(input[a])) {
            number_of_vowels++;
        }
    }
    return number_of_vowels;
}
bool isConsonant (char input) { //if vowels, punctuations and spaces are false then
    // isConsonant is set to true; this code is also limited to :, ;, ', /, @, #, $. %, ^
    // &, *, (, ), -, +, {, {, \, inputs;
    if (isVowel(input) == false && input != '.' && input != '?' && input != '!'
        && input != ' ' && input != ',') {
        return true;
    }
    return false; // else return false;
} //using same format I used for formatting my other functions;
int numConsonants (std::string input) {
    int number_of_consonants = 0;
    for (int a = 0; a <= input.length() - 1; a++) {
        if (isConsonant(input[a])) {
            number_of_consonants++;
        }
    }
    return number_of_consonants;
}
