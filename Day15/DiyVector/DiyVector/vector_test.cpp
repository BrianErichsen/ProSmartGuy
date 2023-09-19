#include "catch_amalgamated.hpp"
#include "MyVector.hpp"
#include <cassert>
TEST_CASE("vector grows to 1000")
{
    MyVector<int> vector;
    REQUIRE(vector.getSize() == 0);

    vector.push_back(1);
    vector.push_back(2);
    vector.push_back(3);
    REQUIRE(vector.getSize() == 3);

    for (int i = 4; i <= 1000; i++)
    {
        vector.push_back(i);
    }

    REQUIRE(vector.getSize() == 1000);
}
TEST_CASE("vec assert") {
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
    //assert(vec.getCapacity() == 4);
}
