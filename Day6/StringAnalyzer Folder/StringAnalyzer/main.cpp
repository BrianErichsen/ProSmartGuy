//
//  main.cpp
//  StringAnalyzer
//
//  Created by Brian Erichsen Fagundes on 8/25/23.
//

#include <iostream>
#include <string>
#include <cctype> // to use the tolower(ch) function;
#include "WordHelpers.hpp"
#include "LetterHelpers.hpp"


int main() {
    std::string s;
    while (s != "done") { //if string is done, the program gets out of the loop;
        std::cout << "Enter a string contanining one or more sentences: ";
        getline(std::cin , s);
        if (s == "done") { // checking for done first before analysing string
            std::cout << "Goodbye" << std::endl; // so that we don't analyse done as well;
            break; // added break because my program wasn't quite finishing on 1st
            // 'done' input. Now with break it does what it supposed to do and exits
            // the loop whenever the user inputs 'done';
        } // no need to a else statement here since I am using break on the above line;
        std::cout << "Analysis: " << std::endl;
        std::cout << "   Number of words: " << numWords(s) << std::endl;
        std::cout << "   Number of sentences: " << numSentences(s) << std::endl;
        std::cout << "   Number of vowels: " << numVowels(s) << std::endl;
        std::cout << "   Number of consonants: " << numConsonants(s) << std::endl;
        double readingLevel = (numConsonants(s) + numVowels(s)) /
        static_cast<double>(numWords(s)); //using proper placement of ();
        // using static_cast<double> for proper double division;
        std::cout << "   Reading level (average word length): " << readingLevel << std::endl;
        double averageVowels = numVowels(s) / static_cast<double>(numWords(s));
        std::cout << "   Average vowels per word: " << averageVowels << std::endl;
    }
    return 0;
}
