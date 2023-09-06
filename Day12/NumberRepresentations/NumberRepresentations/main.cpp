//
//  main.cpp
//  NumberRepresentations
//
//  Created by Brian Erichsen Fagundes on 9/6/23.
//

#include <iostream>
#include <cstdint>
#include <iomanip>
#include <cmath>
#include <fstream>

using namespace std;
bool approxEquals (double a, double b, double tolerance) {
    return abs(a - b) <= tolerance;
}

void testfunction () {
    float j = 0.1;
    float k = 0.2;
    assert(j + k == 0.3);
}
void printFileCharacters (const string& filename) {
    ifstream fin(filename);
    char c;
    while (fin.get(c)) {
        cout << c << endl;
    }
    fin.clear ();
    fin.seekg(0, ios::beg);
    fin.close();
}

int main(int argc, const char * argv[]) {
    int a[] = { 7, 2, -56, 55}; // for every 4 items, 2^
    cout << "size: " << sizeof( a ) << "\n";
    cout << sizeof(int8_t) << " " << sizeof(int16_t) << " " << sizeof(int64_t) << endl;
    cout << sizeof(uint8_t) << " " << sizeof(uint16_t) << " " <<
    sizeof(uint64_t) << endl;
    char b = 0xff;
    cout << b << endl;
    unsigned int maxValue = 0xFFFFFFFF; //if addes one it displays 0 as it's value;
    cout << (maxValue + 1) << endl;
    float j = 0.1;
    float k = 0.2;
    cout << fixed << setprecision(18) << (j + k) << endl;
    double num1 = 10.1;
    double num2 = 10.2;
    double tolerance = 0.2;
    cout << approxEquals(num1, num2, tolerance) << endl;
    char c;
    string filename = "/Users/brianerichsenfagundes/Desktop/UTF.txt";
    printFileCharacters(filename);
    
    return 0;
}
