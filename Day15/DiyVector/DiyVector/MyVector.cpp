//
//  MyVector.cpp
//  DiyVector
//
//  Created by Brian Erichsen Fagundes on 9/13/23.
//
#include <iostream>
#include <cassert>
#include <string>
#include "MyVector.hpp"

//Test Function
void testMyVector () {
    using namespace std;
    MyVector<int> vec;
    // Create a Vector with an initial capacity of 5
    MyVector<int> myVector(5);
    // Test pushBack and get
    vec.push_back(5);
    vec.push_back(10);
    assert(vec.get(0) == 5);
    assert(vec.get(1) == 10);
    // Test set and popBack
    vec.set(2, 15);
    vec.popBack();
    assert(vec.get(0) == 5);
    assert(vec.getSize() == 1);
    // Test growVector
    vec.push_back(20);
    vec.push_back(25);
    // Test free the vector
    myVector.freeVector();
    //test string of vectors concatenation
    MyVector<string> str;
    str.push_back("Hi");
    str.push_back("you!");
    
    MyVector<string> str1;
    str1.push_back("My");
    str1.push_back("cat!");
    MyVector<string> TT = str + str1;
    assert(TT.get(3) == "cat!");
    //Tests != comparison
    assert(TT != str);
    
    cout << "All tests passed!" << endl;
}

