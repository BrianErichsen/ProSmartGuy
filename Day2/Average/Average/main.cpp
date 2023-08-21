//
//  main.cpp
//  Average
//
//  Created by Brian Erichsen Fagundes on 8/21/23.
//

#include <iostream>
using namespace std;

int main() {
    
    int score1, score2, score3, score4, score5;
    cout << "Enter 5 assignment scores (between 0 and 100): ";
    cin >> score1 >> score2 >> score3 >> score4 >> score5;
    
        double average = (score1 + score2 + score3 + score4 + score5) / 5.0;
        cout << "Average: " << average;
    
    return 0;
}
