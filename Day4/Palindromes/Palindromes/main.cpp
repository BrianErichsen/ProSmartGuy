//
//  main.cpp
//  Palindromes
//
//  Created by Brian Erichsen Fagundes on 8/23/23.
//

#include <iostream>
#include <string>
using namespace std;

int main() {
    
    cout << "Enter a word: ";
    string a;
    cin >> a; //usually I would use 'getline(cin, a) but the lab instructions say to assume it's a single word from the user;
    
    string reversed = ""; //Iniatilly this string is empty so that I can store the reverse on it;
    for (int b = a.length() - 1; b >= 0; b--) { //this was a tricky one, here I create a new int that will be the index of the new reversed string; I start off with the length -1 of original input since indexes starts at 0; and go in decreasing increment from there;
        reversed += a[b]; // this line makes the reversed string same as the reverse of original input;
    }
    if (a == reversed) {
        cout << a << " IS a palindrome" << endl;
    }
    else {
        cout << a << " is NOT a palindrome" << endl;
        return 0;
    }
}
