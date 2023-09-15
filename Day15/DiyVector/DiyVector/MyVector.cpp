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
    data = new int[capacity_]; // new T for temolate;
}
MyVector::MyVector(size_t b) {
    if (capacity_ > 0) {
        size_ = 0;
        capacity_ = b;
        data = new int[capacity_];
    }
}
MyVector::MyVector (int* inputdata, size_t size) {
    size_ = 0;
    capacity_ = 10;
    data = new int[capacity_];
    if (inputdata != nullptr) {
        for (int i = 0; i < size; i++) {
            push_back(inputdata[i]);
        }
    }
}
MyVector::MyVector(const std::vector<int>& inputData) {
    size_ = 0;
    capacity_ = 10;
    data = new int[capacity_];
    if (inputData.size() > 0) {
        for (const int& num : inputData) {
            push_back(num);
        }
    }
}
MyVector::~MyVector() {
    std::cout << "Hello from the destructor!" << std::endl;
    size_ = 0;
    capacity_ = 0;
    delete[] data;
    data = nullptr;
}
size_t MyVector::getSize() const {
    return size_;
}
void MyVector::growMyVector () {
    //Creates array temp
    int* temp = new int[capacity_ * 2];
    //Copies data into temp
    for (int i = 0; i < size_; i++) {
        temp[i] = data[i];
    }
    //Delete data
    delete[] data;
    //Point data at whatever temp is pointing at
    data = temp;
    // Set temp to null
    temp = nullptr;
    // Update capacity
    capacity_ *= 2;
}
void MyVector::push_back (int val) {
    if (data == nullptr) {
        MyVector();
    }
    if (size_ + 1 >= capacity_) {
        growMyVector();
    }
    data[size_] = val;
    size_++;
}
void MyVector::deleteVector() {
    size_ = 0;
    delete [] data;
}
void MyVector::popBack() {
    if (size_ > 0) {
        size_--;
    }
}
int MyVector::get(size_t pos) const {
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
        cout << data[i] << " ";
    }
    cout << endl;
}
MyVector operator+(const MyVector& lhs, const MyVector& rhs) {
    assert(lhs.getSize() == rhs.getSize() && "Size mismatch in operator+");
    MyVector result;
    size_t size = lhs.getSize();
    for (size_t i = 0; i < size; i++) {
        result.push_back(lhs.get(i) + rhs.get(i));
    }
    return result;
}
MyVector MyVector::operator+(const MyVector& rhs) {
    assert((size_ == rhs.getSize()) && "Size mismatch in operator+");
    MyVector result;
    size_t size = size_;
    for (size_t i = 0; i < size; i++) {
        result.push_back(data[i] + rhs.get(i));
    }
    return result;
}
//    if (rhs.getSize() == 0)
//        return *this;
//    MyVector result;
//    // First add lhs to result
//    for (size_t i = 0; i < size_; i++) {
//        result.push_back(data[i]);
//    }
//    // Then concatenate rhs
//    for (size_t i = 0; i < size_; i++) {
//        result.push_back(rhs.get(i));
//    }
//    return result;
//}
MyVector& MyVector::operator+=(const MyVector& rhs) {
    for (size_t i = 0; i < rhs.getSize(); i++) {
        push_back(rhs.get(i));
    }
    return *this;
}
int& MyVector::operator[](size_t index) {
    assert(index < size_ && "Out of bounds in operator[]!");
    return data[index];
}
const int& MyVector::operator[](size_t index) const {
    assert(index < size_ && "Out of bounds in operator[]!");
    return data[index];
}
MyVector& MyVector::operator=(const MyVector& rhs) {
    if (this != &rhs) {
        delete [] data;
        size_ = rhs.getSize();
        capacity_ = 2 * size_;
        if(capacity_ > 0) {
            data = new int[capacity_];
            for (size_t i = 0; i < rhs.getSize(); i++) {
                data[i] = rhs[i];
            }
        }
    }
    return *this;
}
MyVector::MyVector(const MyVector& rhs) {
    if (this != &rhs) {
        delete [] data;
        size_ = rhs.getSize();
        capacity_ = 2 * size_;
        if(capacity_ > 0) {
            data = new int[capacity_];
            for (size_t i = 0; i < rhs.getSize(); i++) {
                data[i] = rhs[i];
            }
        }
    }
}
//Test Function
void testMyVector () {
    using namespace std;
    MyVector vec;
    // Create a Vector with an initial capacity of 5
    MyVector myVector(5);
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
}

