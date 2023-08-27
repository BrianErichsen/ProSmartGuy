//
//  main.cpp
//  VectorPractice
//
//  Created by Brian Erichsen Fagundes on 8/26/23.
//

#include <iostream>
#include <string>
#include <vector>
// sums values;
int sum (int a, int b) {
    int total = a + b;
    return total;
}
std::vector<char> stringToVec (const std::string& input) { //using const so that string
    // does not get changed;
    std::vector<char> vector;
    for (char a : input) {
        vector.push_back(a); //using push_back method so that loop addes new char to vector;
    }
    return vector;
}// starting at length index - 1 and decreasing from there; and using push_back method
// so that each last index from original vector matches first index from reversed;
std::vector<int> reverse (const std::vector<int>& input) {
    std::vector<int> reversed;
    for (int a = input.size() - 1; a >= 0; a--) {
        reversed.push_back(input[a]);
    }
    return reversed;
}
int main() {
    std::cout << sum(10, -1) << std::endl;
    std::cout << "Enter a string: " << std::endl;
    
    std::string s;
    std::cin >> s;
    std::vector<char> vector1 = stringToVec(s);
    for (char a : vector1) { //using a 'for each' loop to print each char without dealing
        // with indexes;
        std::cout << a << " ";
    }
    std::cout << std::endl;
    
    std::vector<int> vector2 = {3, 4, 5, 6};
    // for each loop is necessary since we want to print a binary number;
    std::vector<int> reversedVector2 = reverse(vector2);
    for (int x : reversedVector2) {
        std::cout << x << " ";
    }
    
    return 0;
}
