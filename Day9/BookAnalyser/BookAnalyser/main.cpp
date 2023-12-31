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


int main(int argc, const char * argv[]) {
    using namespace std;
    
    std::string filename, word;
    if(argc  == 2){
        filename = argv[1];
        word = argv[2];
    }
    else{
        filename = "Google.txt";
        word = "someword";
    }
//    string word;
    string singleWord; //initialises singleWord sting;
    vector<string> allWords;//initialises allWords;

//    cout << "Enter a word: ";
//    cin >> word;
    
  ifstream fin(filename);
//    ifstream fin("/Users/brianerichsenfagundes/Desktop/Moby.txt");
    //prints an error msg and quits the program;
    if (fin.fail()) {
        cout << "Failed to open file!";
        system("PAUSE");
        return 0;
    }
    while (fin >> singleWord) {
        allWords.push_back(singleWord);
    }
    
    fin.clear();
    fin.seekg(0, std::ios::beg); //Reset file stream to top file;
    vector<int> keywordIndex = keyWordPositions(allWords, word);
     
    cout << "Title: " << getTitle(allWords) << endl;
    cout << getAuthor(allWords) << endl;
    cout << "Total number of words in the file: " << allWords.size() << endl;
    cout << "The total number of characters in the file: " << charNum(allWords) << endl;
    cout << "The shortest word is " << shortestWord(allWords) << " and the longest word is " <<
    longestWord(allWords) << endl;
    cout << "The word " << word << " appears " << numberOfTimesWord(allWords, word) <<
    " times: " << endl;
    printKeyWordPosition(keywordIndex, allWords);

    fin.close();
    
    return 0;
}
