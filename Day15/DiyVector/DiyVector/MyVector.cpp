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
