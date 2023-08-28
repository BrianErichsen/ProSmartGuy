//
//  WordHelpers.cpp
//  StringAnalyzer
//
//  Created by Brian Erichsen Fagundes on 8/28/23.
//

#include "WordHelpers.hpp"
int numWords (std::string input) { //this function has it's own limititaions;
    int number_of_words = 0; // it does not count for double spaces from user;
    for (int a = 0; a <= input.length() - 1; a++) {
        if (input[a] == ' ') {
            number_of_words++;
        }
    }
    return (number_of_words + 1); //word count = number of spaces + 1;
}
int numSentences (std::string input) { // I am using same format that I used on numWords f();
    int number_of_sentences = 0;// this function also has it's own limitations;
    // if inputs contains ... or quotes where a sentence is not finished the output
    // will be off;
    for (int a = 0; a <= input.length() - 1; a++) {
        if (input[a] == '.' || input[a] == '!' || input[a] == '?') {
            number_of_sentences++;
        }
    }
    return number_of_sentences;
}
