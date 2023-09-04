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
#include "words.hpp"
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
     
    cout << "Title: " << getTitle(allWords) << endl;
    cout << "Author: " << getAuthor(allWords) << endl;
    cout << "Total number of words in the file: " << allWords.size() << endl;
    cout << "The total number of characters in the file: " << charNum(allWords) << endl;
    cout << "The shortest word is " << shortestWord(allWords) << endl;

//    cout << total number of words;
//    cout << total number of chars;
//    cout << shortest word in the book;
//    cout << longest word in the book;
//    cout << word; number of appereances, itself + index -1 and +1; % of total number of char;
    fin.close();
    
    return 0;
}
