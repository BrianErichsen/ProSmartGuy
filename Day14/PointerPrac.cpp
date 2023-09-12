#include <iostream>
#include <stdlib.h>

struct MyVector {
  // Start of the array on the leap
  double* data;
  int size;
  int capacity;
};

double arrayModSum (MyVector& vec, int size) {
  // Adds 1.0 into vec each index data
  for (int i = 0; i < size; ++i) {
    vec.data[i] += 1.0;
  }
  // Set sum to 0 first and loops adding data into sum
  double sum = 0.0;
  for (int k = 0; k < size; ++k) {
    sum += vec.data[k];
  }
  return sum;
}
void growMyVector (MyVector& vec) {
  //checks if size equals capacity
  if (vec.size == vec.capacity) {
    // calculate new capacity;
    int newCapacity = vec.capacity * 2;
  // creates temp array, allocates memory
  double* tempData = new double[newCapacity];
  // Copy the contents from vec into new array
  for (int i = 0; i < vec.size; ++i) {
    tempData[i] = vec.data[i];
  }
  // Sets unused spots from temp to a set number; avoiding bugs
  for (int i = vec.size; i < newCapacity; ++i) {
    tempData[i] = -1.0;
  }
  // Calls delete on vec to free up old chunck of memory
  delete [] vec.data;
  // Set input.data equal to the pointer of the temp array
  vec.data = tempData;
  // Set pointer to temp array to nullpr
  tempData = nullptr;
  //Updates capacity
  vec.capacity = newCapacity;
}


int main(int argc, const char * argv[]) {
  // check if at least one argument has been provided
  if (argc < 2) {
    std::cerr << "Error" << '\n';
    // Exists with code that is different than 0
    return 1;
  }
  MyVector vec1;
  vec1.size = 10;
  int arraySize = atoi(argv[1]);
  vec1.data = new double[arraySize];

  vec1.size = arraySize;
  vec1.capacity = arraySize;
  for (int i = 0; i < arraySize; ++i) {
    vec1.data[i] = 1.0;
  }

  std::cout << arrayModSum(vec1, 10) << std::endl;
  growMyVector(vec1);
  std::cout << vec1.capacity << std::endl;
  delete[] vec1.data;

  return 0;
}
