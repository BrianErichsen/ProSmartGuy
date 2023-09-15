.PHONY: all clean

CXX = clang++
CXXFLAGS = -Wall -std=c++14

all: vector_test

catch_amalgamated.o: catch_amalgamated.hpp catch_amalgamated.cpp
	$(CXX) $(CXXFLAGS) -c catch_amalgamated.cpp -o $@

MyVector.o: MyVector.hpp MyVector.cpp
	$(CXX) $(CXXFLAGS) -c MyVector.cpp -o $@

vector_test: MyVector.o catch_amalgamated.o vector_test.cpp
	$(CXX) $(CXXFLAGS) vector_test.cpp MyVector.o catch_amalgamated.o -o $@

test: vector_test
	./vector_test

clean:
	rm -f *.o vector_test
