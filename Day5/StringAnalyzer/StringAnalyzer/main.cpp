//
//  main.cpp
//  StringAnalyzer
//
//  Created by Brian Erichsen Fagundes on 8/25/23.
//

#include <iostream>
#include <string>
#include <cctype> // to use the tolower(ch) function;
using namespace std;
//finds the number of words from input;
int numWords (string input) { //this function has it's own limititaions;
    int number_of_words = 0; // it does not count for double spaces from user;
    for (int a = 0; a <= input.length() - 1; a++) {
        if (input[a] == ' ') {
            number_of_words++;
        }
    }
    return (number_of_words + 1); //word count = number of spaces + 1;
}
int numSentences (string input) { // I am using same format that I used on numWords f();
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
bool isVowel (char input) { // a, i, u, e, o y vowels, if char == vowels bool returns true;
    input = tolower(input);
    if (input == 'a' || input == 'e' ||
        input == 'i' || input == 'o' ||
        input == 'u' || input == 'y') {
        return true;
    }
    return false; // else returns false;
} // using same format for numVowels function; using bool isVowel to add number to count;
int numVowels (string input) {
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
int numConsonants (string input) {
    int number_of_consonants = 0;
    for (int a = 0; a <= input.length() - 1; a++) {
        if (isConsonant(input[a])) {
            number_of_consonants++;
        }
    }
    return number_of_consonants;
}

int main() {
    string s;
    while (s != "done") { //if string is done, the program gets out of the loop;
        cout << "Enter a string contanining one or more sentences: ";
        getline(cin , s);
        if (s == "done") { // checking for done first before analysing string
            cout << "Goodbye" << endl; // so that we don't analyse done as well;
            break; // added break because my program wasn't quite finishing on 1st
            // 'done' input. Now with break it does what it supposed to do and exits
            // the loop whenever the user inputs 'done';
        } // no need to a else statement here since I am using break on the above line;
        cout << "Analysis: " << endl;
        cout << "   Number of words: " << numWords(s) << endl;
        cout << "   Number of sentences: " << numSentences(s) << endl;
        cout << "   Number of vowels: " << numVowels(s) << endl;
        cout << "   Number of consonants: " << numConsonants(s) << endl;
        double readingLevel = (numConsonants(s) + numVowels(s)) /
        static_cast<double>(numWords(s)); //using proper placement of ();
        // using static_cast<double> for proper double division;
        cout << "   Reading level (average word length): " << readingLevel << endl;
        double averageVowels = numVowels(s) / static_cast<double>(numWords(s));
        cout << "   Average vowels per word: " << averageVowels << endl;
    }
    return 0;
}
