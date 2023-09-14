//
//  MyVector.cpp
//  DiyVector
//
//  Created by Brian Erichsen Fagundes on 9/13/23.
//
#include <iostream>
#include <cassert>
#include "MyVector.hpp"
MyVector::MyVector() {
    size_ = 0;
    capacity_ = 10;
    data = new int[capacity_];
}
MyVector::MyVector(size_t b) {
    size_ = 0;
    capacity_ = b;
    data = new int[capacity_];
}
MyVector::~MyVector() {
    std::cout << "Hello from the destructor!" << std::endl;
    size_ = 0;
    capacity_ = 0;
    delete[] data;
}
size_t MyVector::getSize() const {
    return size_;
}
void MyVector::growMyVector () {
    int* temp = new int[capacity_ * 2];
    for (int i = 0; i < size_; i++) {
        temp[i] = data[i];
    }
    delete[] data;
    data = temp;
    temp = nullptr;
    capacity_ *= 2;
}
void MyVector::push_back (int val) {
    if (size_ + 1 == capacity_) {
        growMyVector();
    }
    data[size_] = val;
    size_++;
}
void MyVector::deleteVector() {
    size_ = 0;
    delete [] data;
}
MyVector MyVector::makeVector(size_t size) {
    capacity_ = 2 * size_;
    size_ = size;
    data = new int[capacity_];
    return *this;
}
void MyVector::popBack() {
    if (size_ > 0) {
        size_--;
    }
}
int MyVector::get(size_t pos) {
    assert (pos < size_ && "Invalid position");
    assert (data != nullptr && "Vector is empty");
    return data[pos];
}
size_t MyVector::getCapacity() const {
    return capacity_;
}
void MyVector::set(int val, size_t pos) {
    if (pos < size_) {
        data[pos] = val;
    }
    else { //handle out of bound case
        std::out_of_range("Position is out of bounds");
    }
}
//Delocate any heap memory used by
void MyVector::freeVector () {
    delete[] data;
    data = nullptr;
}
void MyVector::printVec() const {
    using namespace std;
    for (size_t i = 0; i < size_; i++){
        cout << data[i] << " " << endl;
    }
}
//void testMyVector() {
//    // Create a MyVector object for testing
//    MyVector vec;
//
//    // Test the default constructor and getSize function
//    assert(vec.getSize() == 0);
//
//    // Test push_back and getSize functions
//    vec.push_back(1);
//    vec.push_back(2);
//    vec.push_back(3);
//    assert(vec.getSize() == 3);
//
//    // Test get function
//    assert(vec.get(0) == 1);
//    assert(vec.get(1) == 2);
//    assert(vec.get(2) == 3);
//
//    // Test set function
//    vec.set(10, 1);
//    assert(vec.get(1) == 10);
//
//    // Test popBack function
//    vec.popBack();
//    assert(vec.getSize() == 2);
//    assert(vec.get(2) != 3); // Ensure the last element is removed
//    // Add more test cases for your functions as needed
//
//    // Test deleteVector function
//    vec.deleteVector();
//    assert(vec.getSize() == 0);
//    // Make sure to add more comprehensive tests for various scenarios
//
//    // Test printVec function (manually inspect output)
//    vec.printVec();
//
//    std::cout << "All tests passed!" << std::endl;
//}

