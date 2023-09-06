//
//  main.cpp
//  NumberConverter
// Created by Brian Erichsen Fagundes &  Mina Akbari;
//  Created by Brian Erichsen Fagundes on 9/5/23.
//

#include <iostream>
#include <string>
#include <cctype>
#include <vector>
#include <math.h>
#include <cmath>

using namespace std;

int charToInt (char z) {
    if (z >= '0' && z <= '9') {
        return z - '0'; // '0' has value 0 on ASCII table;
    }
    else if (z >= 'A' && z <= 'F') {
        return z - 'A' + 10; //if character between A and F, returns itself - 'A' + 10;
    }
    else if (z >= 'a' && z <= 'f') { //same logic but also counting for lower case char;
        return z - 'a' + 10;
    }
    else {
        return - 1; //returns error value;
    }
}
int stringToInt(string stringOfDigits, int base) {
    int result = 0;
    int power = 1; //remember that base^0 = 1; that's why power is initiallized as 1 first;
    if (base == 10) { //if base is 10, it's int value is just itself;
        int integer= stoi(stringOfDigits);
        return integer;
    } // reverses the string stringOfDigitis;
    for (int i = stringOfDigits.length() - 1; i >= 0; --i) {
        int digit =  charToInt(stringOfDigits[i]); //assigns new reversed order to int digit;
        result += digit * power; // adds digit itself * power;
        power *= base; //changes power value to itself times itself each interaction;
    }
    return result;
}
string intToDecimalString (int decimal) { //takes an int and return its string'
    string numberString = to_string(decimal); //uses to_string();
    return numberString;
}
string intToBinaryString (int decimal) {
    string binary; //Initialises string as empty first;
    while (decimal != 0) { //once decimal is 0 then it has it's binary value assigned already;
        //if remainder of 2 is 0, then assigns 0 to append or 1 if contrary; appends each loop keep-ing values from left to right;
        binary = (decimal % 2 == 0 ? "0" : "1") + binary;
        // repeatly divides by 2 and appends the remainder 0 or 1 to the left until decimal becomes-
        // 0;
        decimal /= 2;
    }
    return binary;
}
string intToHexadecimalString (int decimal) {
    string hexString; //Initialises string as empty first;
    const char HexDigits[] = "0123456789ABCDEF"; //Initialises ARRAY with proper index to each Hex;
    bool isNegative = false; //Initialises isNegative to be false first to handle negative input;
    if (decimal < 0) {
        isNegative = true;
        decimal = -decimal; //if negative, then decimal = to it's absolute value;
    }
    while (decimal > 0) { // Once decimal reaches 0 there is no more remainder;
        int remainder = decimal % 16; // remainder from 0 to 15 in each interaction;
        hexString = HexDigits[remainder] + hexString; // appends it's corresponding hexDigit from
        // Array and appends itself from left to right;
        decimal /= 16; //divides decimal by 16 in each interaction;
    }
    if (isNegative) { //appends "-" sign whenever isNegative is true;
        hexString = "-" + hexString;
    }
    return hexString;
}
void testFunctions() {
    // Test charToInt function;
    assert(charToInt('0') == 0);
    assert(charToInt('5') == 5);
    assert(charToInt('9') == 9);
    assert(charToInt('A') == 10);
    assert(charToInt('F') == 15);
    assert(charToInt('a') == 10);
    assert(charToInt('f') == 15);
    assert(charToInt('G') == -1);
    // Test stringToInt function;
    assert(stringToInt("123", 10) == 123);
    assert(stringToInt("1A", 16) == 26);
    assert(stringToInt("a5", 16) == 165);
    assert(stringToInt("XYZ", 10) == -1);
    //Test intToDecimalString function
    assert(intToDecimalString(123) == "123");
    assert(intToDecimalString(-456) == "-456");
    assert(intToDecimalString(0) == "0");
    // Test intToBinaryString function;
    assert(intToBinaryString(10) == "1010");
    assert(intToBinaryString(255) == "11111111");

}

int main(int argc, const char * argv[]) {
    string digits;
    int base;
    cout << "Enter a string of digits & and it's base: ";
    cin >> digits >> base;
    cout << stringToInt(digits, base) << endl;
    cout << intToDecimalString(10) << endl;
    cout << intToBinaryString(77777) << endl;
    cout << intToHexadecimalString(999) << endl;
    return 0;
}
