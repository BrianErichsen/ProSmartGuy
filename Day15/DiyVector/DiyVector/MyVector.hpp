//
//  MyVector.hpp
//  DiyVector
//
//  Created by Brian Erichsen Fagundes on 9/13/23.
//

#ifndef MyVector_hpp
#define MyVector_hpp

#include <stdio.h>
class MyVector {
private:
    //a member variable
    int* data;
    //keeps track of how many slots are being used
    size_t size_;
    //Keeps track of how many slots does it have in total
    size_t capacity_;
    // Constructor
    void growMyVector ();
/*---------------------- beguinning of public */
public:
    MyVector();
    MyVector(size_t b);
    //Destructor
    ~MyVector();
    // function that returns size and does not alter internal state of object
    MyVector makeVector(size_t size);
    void push_back (int val);
    size_t getSize() const;
    void deleteVector();
    void set(int val, size_t pos);
};
void growMyVector (MyVector& vec);
#endif /* MyVector_hpp */
