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
    MyVector(); // constructor
    
    //another constructor (equivalent to reserve)
    MyVector(size_t capacity);
    
   //Takes an vector and create a MyVector
    MyVector(const std::vector<int>& inputData);
    
    // Takes an array in and create a vector out of that
    MyVector (int* inputdata, size_t size);
    
    // Copy constructor
    MyVector(const MyVector& rhs);
    
    //Destructor
    ~MyVector();
    
    //Operators
    MyVector operator+(const MyVector& rhs);
    MyVector& operator+=(const MyVector& rhs);
    int& operator[](size_t index);
    const int& operator[] (size_t index) const;
    MyVector& operator=(const MyVector& rhs);
    bool operator==(const MyVector& rhs) const;
    bool operator!=(const MyVector& rhs) const;
    bool operator<(const MyVector& rhs) const;
    bool operator<=(const MyVector& rhs) const;
    bool operator>(const MyVector& rhs) const;
    bool operator>=(const MyVector& rhs) const;
    //Users class methods
    void push_back (int val);
    // Gets size and does not alter internal state of object
    size_t getSize() const;
    void deleteVector();
    int get(size_t pos) const;
    void popBack();
    size_t getCapacity() const;
    void set(int val, size_t pos);
    void freeVector ();
    void printVec() const;
};
void testMyVector();
MyVector operator+(const MyVector& lhs, const MyVector& rhs);
#endif /* MyVector_hpp */
