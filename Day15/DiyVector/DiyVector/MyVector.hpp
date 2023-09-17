//
//  MyVector.hpp
//  DiyVector
//
//  Created by Brian Erichsen Fagundes on 9/13/23.
//

#ifndef MyVector_hpp
#define MyVector_hpp

#include <stdio.h>
template<typename T>
class MyVector {
private:
    //a member variable
    T* data;
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
    MyVector(const std::vector<T>& inputData);
    
    // Takes an array in and create a vector out of that
    MyVector (T* inputdata, size_t size);
    
    // Copy constructor
    MyVector(const MyVector& rhs);
    
    //Destructor
    ~MyVector();
    
    //Operators
    MyVector<T> operator+(const MyVector<T>& rhs) const;
    MyVector<T>& operator+=(const MyVector<T>& rhs);
    T& operator[](size_t index);
    const T& operator[] (size_t index) const;
    MyVector<T>& operator=(const MyVector<T>& rhs);
    bool operator==(const MyVector& rhs) const;
    bool operator!=(const MyVector& rhs) const;
    bool operator<(const MyVector& rhs) const;
    bool operator<=(const MyVector& rhs) const;
    bool operator>(const MyVector& rhs) const;
    bool operator>=(const MyVector& rhs) const;
    //Users class methods
    void push_back (T val);
    // Gets size and does not alter internal state of object
    size_t getSize() const;
    void deleteVector();
    T get(size_t pos) const;
    void popBack();
    size_t getCapacity() const;
    void set(T val, size_t pos);
    void freeVector ();
    void printVec() const;
};
void testMyVector();
template<typename T>
MyVector<T>::MyVector() {
    size_ = 0;
    capacity_ = 10;
    data = new T[capacity_];
}
template<typename T>
MyVector<T>::MyVector(size_t b) {
    if (capacity_ > 0) {
        size_ = 0;
        capacity_ = b;
        data = new int[capacity_];
    }
}
template<typename T>
MyVector<T>::MyVector (T* inputdata, size_t size) {
    size_ = 0;
    capacity_ = 10;
    data = new T[capacity_];
    if (inputdata != nullptr) {
        for (int i = 0; i < size; i++) {
            push_back(inputdata[i]);
        }
    }
}
template<typename T>
MyVector<T>::MyVector(const std::vector<T>& inputData) {
    size_ = 0;
    capacity_ = 10;
    data = new T[capacity_];
    if (inputData.size() > 0) {
        for (const T& num : inputData) {
            push_back(num);
        }
    }
}
template<typename T>
MyVector<T>::~MyVector() {
    size_ = 0;
    capacity_ = 0;
    delete[] data;
    data = nullptr;
}
template<typename T>
size_t MyVector<T>::getSize() const {
    return size_;
}
template<typename T>
void MyVector<T>::growMyVector () {
    //Creates array temp
    T* temp = new T[capacity_ * 2];
    //Copies data into temp
    for (size_t i = 0; i < size_; i++) {
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
template<typename T>
void MyVector<T>::push_back (T val) {
    if (data == nullptr) {
        MyVector();
    }
    if (size_ + 1 >= capacity_) {
        growMyVector();
    }
    data[size_] = val;
    size_++;
}
template<typename T>
void MyVector<T>::deleteVector() {
    size_ = 0;
    delete [] data;
}
template<typename T>
void MyVector<T>::popBack() {
    assert(size_ != 0 && "Vector is empty");
        size_--;
}
template <typename T>
T MyVector<T>::get(size_t pos) const {
    assert (pos < size_ && "Invalid position");
    assert (data != nullptr && "Vector is empty");
    return data[pos];
}
template <typename T>
size_t MyVector<T>::getCapacity() const {
    return capacity_;
}
template <typename T>
void MyVector<T>::set(T val, size_t pos) {
    if (pos < size_) {
        data[pos] = val;
    }
    else { //handle out of bound case
        std::out_of_range("Position is out of bounds");
    }
}
//Delocate any heap memory used by
template <typename T>
void MyVector<T>::freeVector () {
    delete[] data;
    data = nullptr;
}
template <typename T>
void MyVector<T>::printVec() const {
    using namespace std;
    for (size_t i = 0; i < size_; i++){
        cout << data[i] << " ";
    }
    cout << endl;
}
//MyVector operator+(const MyVector& lhs, const MyVector& rhs) {
//    assert(lhs.getSize() == rhs.getSize() && "Size mismatch in operator+");
//    MyVector result;
//    size_t size = lhs.getSize();
//    for (size_t i = 0; i < size; i++) {
//        result.push_back(lhs.get(i) + rhs.get(i));
//    }
//    return result;
//}
template <typename T>
MyVector<T> MyVector<T>::operator+(const MyVector<T>& rhs) const {
    if (rhs.getSize() == 0)
        return *this;
    MyVector result;
    // First add lhs to result
    for (size_t i = 0; i < size_; i++) {
        result.push_back(data[i]);
    }
    // Then concatenate rhs
    for (size_t i = 0; i < size_; i++) {
        result.push_back(rhs.get(i));
    }
    return result;
}
template<typename T>
MyVector<T>& MyVector<T>::operator+=(const MyVector<T>& rhs) {
    for (size_t i = 0; i < rhs.getSize(); i++) {
        push_back(rhs.get(i));
    }
    return *this;
}
template<typename T>
T& MyVector<T>::operator[](size_t index) {
    assert(index < size_ && "Out of bounds in operator[]!");
    return data[index];
}
template<typename T>
const T& MyVector<T>::operator[](size_t index) const {
    assert(index < size_ && "Out of bounds in operator[]!");
    return data[index];
}
template<typename T>
MyVector<T>& MyVector<T>::operator=(const MyVector<T>& rhs) {
    if (this == &rhs) {
        return *this;
    }
    if (this != &rhs) {
        delete [] data;
        size_ = rhs.getSize();
        capacity_ = 2 * size_;
        
        if(capacity_ > 0) {
            data = new T[capacity_];
            for (size_t i = 0; i < rhs.getSize(); i++) {
                data[i] = rhs[i];
            }
        }
    }
    return *this;
}
template<typename T>
MyVector<T>::MyVector(const MyVector<T>& rhs) {
    if (this != &rhs) {
        delete [] data;
        size_ = rhs.getSize();
        capacity_ = 2 * size_;
        
        if(capacity_ > 0) {
            data = new T[capacity_];
            for (size_t i = 0; i < rhs.getSize(); i++) {
                data[i] = rhs[i];
            }
        }
    }
}
template<typename T>
bool MyVector<T>::operator==(const MyVector<T>& rhs) const {
    if (size_ != rhs.getSize()) {
        // if they have different sizes, they are not equal by default
        return false;
    }
    //If any element is differnet, then they are not equal
    for (size_t i = 0; i < rhs.getSize(); i++) {
        if (data[i] != rhs[i]) {
            return false;
        }
    }
    return true;
}
template<typename T>
bool MyVector<T>::operator!=(const MyVector<T>& rhs) const {
    return !(*this == rhs);
}
template<typename T>
// compares if lhs is lower than rhs
bool MyVector<T>::operator<(const MyVector<T>& rhs) const {
    size_t size = size_;
    //if size of lhs is lower than rhs, then returns true
    if (rhs.getSize() > size) {
        return true;
    }
    //compares each other's data lexicographically
    //Iterates through each element until finds different values for data
    // then compares if lhs data is lower than rhs
    for (size_t i = 0; i < size; ++i) {
        if (data[i] != rhs[i] && data[i] < rhs[i]) {
            return true;
        }
    }
    return false;
}
template<typename T>
bool MyVector<T>::operator<=(const MyVector<T>& rhs) const {
    // returns true if lhs and rhs are either equals or if lhs is lower than rhs
    return (*this == rhs) || (*this < rhs);
}
template<typename T>
bool MyVector<T>::operator>(const MyVector<T>& rhs) const {
    size_t size = size_;
    //If size of lhs is > than rhs, then returns true
    if (rhs.getSize() < size) {
        return true;
    }
    //Does a lexicographic comparison and when data is different but rhs data is > than lhs
    // then returns true
    for (size_t i = 0; i < size; ++i) {
            if (data[i] != rhs && data[i] > rhs[i]) {
                return true;
            }
    }
    return false;
}
template<typename T>
//if lhs is == to rhs or if lhs is > than rhs then it returns true
bool MyVector<T>::operator>=(const MyVector<T>& rhs) const {
    return (*this == rhs) || (*this > rhs);
}

#endif /* MyVector_hpp */
