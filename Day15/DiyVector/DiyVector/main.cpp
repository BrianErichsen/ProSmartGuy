//
//  main.cpp
//  DiyVector
//
//  Created by Brian Erichsen Fagundes on 9/12/23.
// This program makes my own class; vector;

#include <iostream>
#include "MyVector.hpp"

    
int main(int argc, const char* argv[]) {
    using namespace std;
    MyVector v;
    v.push_back(1);
    v.push_back(2);
    v.push_back(3);
    v.push_back(4);
    v.push_back(5);
    MyVector v1;
    v1.push_back(1);
    v1.push_back(2);
    v1.push_back(3);
    v1.push_back(6666);
    v1.push_back(5);
    if (v != v1) {
        cout << "Yes, they are not equal";
    }
    else
        cout << "Nah, they are not equal";
    cout << v1.get(3) << endl;
//    v.set(420, 0);
//    std::vector<int> a = {8, 9, 10, 11, 12};
//    MyVector v4(a);
//    v4.printVec();
//    MyVector v3 = v + v1;
//    v3.printVec();
//    cout << v.getSize() << " " << v.getCapacity() << " ";
//    v.printVec();
    
    cout << "I love learning this!" << endl;
        
    return 0;
}
