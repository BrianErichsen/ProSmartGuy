//
//  words.cpp
//  BookAnalyser
//
//  Created by Brian Erichsen Fagundes on 9/1/23.
//

#include "words.hpp"
#include <string>
#include <vector>
#include <algorithm>
#include <iomanip>
#include <iostream>

using namespace std;

string getTitle(const vector<string>& input) {
    string title;
    // Initialises as false first and check conditions;
    bool foundTitle = false;
    // keeps track of how many words in total for > 100;
    int wordCount = 0;
    
    //loops through each index on vector;
    for (const string& word : input) {
        string lowercaseWord = word; //to lower;
        // https://www.geeksforgeeks.org/conversion-whole-string-uppercase-lowercase-using-stl-c/#
// transform ::tolower syntax reference;
        transform(lowercaseWord.begin(), lowercaseWord.end(), lowercaseWord.begin(), ::tolower);
        //tries to find "Title"
        if (lowercaseWord.find("title:") != string::npos) {
            foundTitle = true;
            //Skip the Title: word itself;
            continue;
        } /*std::string::npos means until the end of the string, usually used to indicate no matches;
        checks if word contains substring "author" otherwise returns std::string::npos;
        breaks out of the loop so it stops appending words after finding "Author";*/
        if (foundTitle && lowercaseWord.find("author:") != string::npos) {
            break;
        } //if title is not empty, it adds spaces after first word;
        if (foundTitle) {
            if (!title.empty()) {
                title += " ";
            } // add words after first word;
            title += word;
        }
        wordCount++;
        //if word count > 100; it clears sring title and exits the loop;
        if (wordCount >= 100) {
            title.clear();
            break;
        }
    }
    /*conditional statement where it checks if title is empty, if true first statement is executed and if not the second statement is executed;*/
    return title.empty() ? "Unknown Title" : title;
}
string getAuthor (const vector<string>& input) {
    string author;
    //initialises isAuthor to be false first;
    bool isAuthor = false;
    for (const string& word : input) {
        string lowercaseWord = word;
        transform(lowercaseWord.begin(), lowercaseWord.end(), lowercaseWord.begin(), ::tolower);
        // checks for word author and enters IsAuthor section when author is found;
        if (lowercaseWord.find("author:") == 0) {
            isAuthor = true;
        } // ends author section whenever release word is found;
        if (lowercaseWord.find("release") == 0) {
            break;
        } // appends all words in between author, plus it adds spaces;
        if (isAuthor) {
            author += word + " ";
        }
    }
    return author.empty() ? "Unknown Author" : author;
}
int charNum (const vector<string>& input) {
    //sets total char initially to be 0;
    int totalchar = 0;
    for (const string& word : input) {
        // loop adds total word size to total characters in each interaction;
        totalchar += word.size();
    }
    return totalchar;
}
string shortestWord (const vector<string>& input) {
    //sets smallest to be index 0 so it's size can be compared to each index;
    string smallest = input[0];
    for (const string& word : input) {
        // if size is lower then smallest = word;
        if (word.size() < smallest.size()) {
            smallest = word;
        }
    }
    return smallest;
}
string longestWord (const vector<string>& input) {
    //sets longest to be index 0 so it's size can be compared to each index;
    string longest = input[0];
    for (const string& word : input) {
        //if size is longer then longer = word;
        if (word.size() > longest.size()) {
            longest = word;
        }
    }
    return longest;
}
int numberOfTimesWord (const vector<string>& input, const string& lookFor) { //sets initial count to be 0;
    int numberWord = 0;
    // for comparison starting at index 0;
    string p1 = input[0];
    for (const string& word : input) {
        //whenever word lookfor is found, increase count by one;
        if (word == lookFor) {
            numberWord++;
        }
    }
    return numberWord;
}
vector<int> keyWordPositions (const vector<string>& input, const string& keyword) {
    //creates a vector that contains all the keyword indexes;
    vector<int> keywordIndex;
    for (int i = 0; i < input.size(); ++i) {
        if (input[i] == keyword) {
            keywordIndex.push_back(i);
        }
    }
    return keywordIndex;
}
void printKeyWordPosition (const vector<int>& keywordIndex, const vector<string>& input) {
    for (int b = 0; b < keywordIndex.size(); ++ b) {
        //sets index to be b which will run through the whole keywordindex vec;
        int index = keywordIndex[b];
        //calculates percentage compared to total words;
        double percentage = static_cast<double>(index + 1) * 100 / input.size();
        //sets percentage to be printed as one digit number;
        cout << "at " << fixed << setprecision(0) << percentage << "%: ";
        // prints all adjacents sides of index;
        if (index > 0) {
            cout << input[index - 1] << " ";
        }
        cout << input[index] << " ";
        if (index < input.size() - 1) {
            cout << input[index + 1];
        }
        cout << endl;
    }
}
