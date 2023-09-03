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
string getAuthor(const vector<string>& input) {
    string author;
    bool foundAuthor = false;
    int wordCount = 0;
    for (const string& word : input) {
        string lowercaseWord = word;
        transform(lowercaseWord.begin(), lowercaseWord.end(), lowercaseWord.begin(), ::tolower);
        if (lowercaseWord.find("author:") != string::npos) {
            foundAuthor = true;
            continue;
        }
        if (foundAuthor && lowercaseWord.find("release date:") != string::npos) {
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
    return author.empty() ? "Unknown author" : author;
}
// Author: Herman Melville
// Release Date: June,
//Release Date: June, 2001 [eBook #2701]
