//
//  words.cpp
//  BookAnalyser
//
//  Created by Brian Erichsen Fagundes on 9/1/23.
//

#include "words.hpp"
#include <string>
#include <vector>

using namespace std;

string getTitle(const vector<string>& input) {
    string title;
    bool foundTitle = false; // Initialises as false first and check conditions;
    int wordCount = 0; // keeps track of how many words in total for > 100;
    for (const string& word : input) { //loops through each index on vector;
        if (word.find("Title:") != string::npos) { //tries to find "Title"
            foundTitle = true;
            continue; //Skip the Title: word itself;
        } //std::string::npos means until the end of the string, usually used to indicate no matches;
        if (foundTitle && word.find("Author:") != string::npos) { // checks if word contains
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
string getAuthor(const vector<string>& input) {
    string author;
    bool foundAuthor = false;
    int wordCount = 0;
    for (const string& word : input) {
        if (word.find("Author:") != string::npos) {
            foundAuthor = true;
            continue;
        }
        if (foundAuthor && word.find("Release date:") != string::npos) {
            break;
        }
        if (foundAuthor) {
            if (!author.empty()) {
                author += " ";
            }
            author += word;
        }
        wordCount++;
        if (wordCount >= 100) {
            author.clear();
            break;
        }
    }
    return author.empty() ? "Unknow author" : author;
}
