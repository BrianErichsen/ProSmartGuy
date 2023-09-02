//
//  main.cpp
//  BookAnalyser
//
//  Created by Brian Erichsen Fagundes on 9/1/23.
//

#include <iostream>
#include <fstream>
#include <vector>
#include <string>

using namespace std;


//int numWords (std::string input) { //this function has it's own limititaions;
//    int number_of_words = 0; // it does not count for double spaces from user;
//    for (int a = 0; a <= input.length() - 1; a++) {
//        if (input[a] == ' ') {
//            number_of_words++;
//        }
//    }
//    return (number_of_words + 1); //word count = number of spaces + 1;
//}
//int numSentences (std::string input) { // I am using same format that I used on numWords f();
//    int number_of_sentences = 0;// this function also has it's own limitations;
//    // if inputs contains ... or quotes where a sentence is not finished the output
//    // will be off;
//    for (int a = 0; a <= input.length() - 1; a++) {
//        if (input[a] == '.' || input[a] == '!' || input[a] == '?') {
//            number_of_sentences++;
//        }
//    }
//    return number_of_sentences;
//}

int main(int argc, const char * argv[]) {
//    string filename(argv[1]);
//    string word(argv[2]);
    string word;
    string singleWord; //initialises singleWord sting;
    vector<string> allWords;//initialises allWords;

    cout << "Enter a word: ";
    cin >> word;
    
//     ifstream fin(filename);
    ifstream fin("/Users/brianerichsenfagundes/Desktop/Moby.txt");
    
    if (fin.fail()) {
        cout << "Failed to open file!";
        system("PAUSE");
        return 0; //prints an error msg and quits the program;
    }
    while (fin >> singleWord) {
        allWords.push_back(singleWord);
    }
    
    fin.clear();
    fin.seekg(0, std::ios::beg); //Reset file stream to top file;
     
//    cout << isTitle(vector<string>);
//    cout << authorOftheBook;
//    cout << total number of words;
//    cout << total number of chars;
//    cout << shortest word in the book;
//    cout << longest word in the book;
//    cout << word; number of appereances, itself + index -1 and +1; % of total number of char;
    
    cout << allWords.size() << endl;
    fin.close();
    
    return 0;
}
