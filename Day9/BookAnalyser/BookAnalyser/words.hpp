//
//  words.hpp
//  BookAnalyser
//
//  Created by Brian Erichsen Fagundes on 9/1/23.
//

#ifndef words_hpp
#define words_hpp
#include <string>
#include <vector>

using namespace std;

#include <stdio.h>
string getTitle(const vector<string>& input);
string getAuthor(const vector<string>& input);
int charNum (const vector<string>& input);
string shortestWord (const vector<string>& input);
string longestWord (const vector<string>& input);
int numberOfTimesWord (const vector<string>& input, const string& lookFor);
vector<int> keyWordPositions (const vector<string>& input, const string& keyword);
void printKeyWordPosition (const vector<int>& keywordIndex, const vector<string>& input);
#endif /* words_hpp */
