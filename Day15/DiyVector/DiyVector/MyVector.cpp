//
//  MyVector.cpp
//  DiyVector
//
//  Created by Brian Erichsen Fagundes on 9/13/23.
//

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
