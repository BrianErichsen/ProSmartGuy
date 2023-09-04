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

using namespace std;

string getTitle(const vector<string>& input) {
    string title;
    bool foundTitle = false; // Initialises as false first and check conditions;
    int wordCount = 0; // keeps track of how many words in total for > 100;
    for (const string& word : input) { //loops through each index on vector;
        string lowercaseWord = word;
        // https://www.geeksforgeeks.org/conversion-whole-string-uppercase-lowercase-using-stl-c/#
//        transform ::tolower syntax reference;
        transform(lowercaseWord.begin(), lowercaseWord.end(), lowercaseWord.begin(), ::tolower);
        if (lowercaseWord.find("title:") != string::npos) { //tries to find "Title"
            foundTitle = true;
            continue; //Skip the Title: word itself;
        } //std::string::npos means until the end of the string, usually used to indicate no matches;
        if (foundTitle && lowercaseWord.find("author:") != string::npos) { // checks if word contains
            // substring "author" otherwise returns std::string::npos
            break; //breaks out of the loop so it stops appending words after finding "Author";
        }
        if (foundTitle) {
            if (!title.empty()) { //if title is not empty, it adds spaces after first word;
                title += " ";
            }
            title += word; // add words after first word
        }
        wordCount++;
        if (wordCount >= 100) { //if word count > 100; it clears sring title and exits the loop;
            title.clear();
            break;
        }
    }
    return title.empty() ? "Unknown Title" : title; //conditional statement where it checks if title is empty, if true first statement is executed and if not the second statement is executed;
}
string getAuthor (const vector<string>& input) {
    string author;
    int wordCount = 0;
    bool isAuthor = false; //initialises isAuthor to be false first;
    for (const string& word : input) {
        string lowercaseWord = word;
        transform(lowercaseWord.begin(), lowercaseWord.end(), lowercaseWord.begin(), ::tolower);
        if (lowercaseWord.find("author:") == 0) {
            isAuthor = true; // checks for word author and enters IsAuthor section when author is found;
        }
        if (lowercaseWord.find("release") == 0) {
            isAuthor = false; // ends author section whenever release word is found;
        }
        if (isAuthor) {
            author += word + " "; // appends all words in between author: and release including spaces;
        }
        wordCount++; //each interaction increments word count; if > 100 it empty the author's name and exits loop;
        if (wordCount >= 100) {
            author.clear();
            break;
        }
    }
    return author.empty() ? "Unknown Title" : author;
}
int charNum (const vector<string>& input) {
    int totalchar = 0; //sets total char initially to be 0;
    for (const string& word : input) {
        totalchar += word.size(); // loop adds total word size to total characters in each interaction;
    }
    return totalchar;
}
string shortestWord (const vector<string>& input) {
    string smallest = input[0]; //sets smallest to be index 0 so it's size can be compared to each index;
    for (const string& word : input) {
        if (word.size() < smallest.size()) { // if size is lower then smallest = wordl;
            smallest = word;
        }
    }
    return smallest;
}
string longestWord (const vector<string>& input) {
    string longest = input[0]; //sets longest to be index 0 so it's size can be compared to each index;
    for (const string& word : input) {
        if (word.size() > longest.size()) { //if size is longer then longer = word;
            longest = word;
        }
    }
    return longest;
}
