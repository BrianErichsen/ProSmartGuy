//
//  ConverterFunctions.cpp
//  NumberConverter
//
//  Created by Brian Erichsen Fagundes on 9/5/23.
//

#include "ConverterFunctions.hpp"
#include <vector>
#include <string>

int charToInt (char z) {
    //Finds proper return value of z depending if int or char
    //If char is digit returns itself
    if (z >= '0' && z <= '9') {
        return z - '0'; // '0' has value 0 on ASCII table;
    }
    //If char is char, then assigns int value of itself - 'A' or 10 + 10
    else if (z >= 'A' && z <= 'F') {
        return z - 'A' + 10;
    }
    //Same logic but also counting for lower case char
    else if (z >= 'a' && z <= 'f') {
        return z - 'a' + 10;
    }
    //If char is non conventional input
    else {
        return - 1; //returns error value;
    }
}
int stringToInt(std::string stringOfDigits, int base) {
    // base^0 = 1
    int result = 0;
    int power = 1;
    //If base is 10, it's int value is just itself
    if (base == 10) {
        int integer= stoi(stringOfDigits);
        return integer;
    }
    // reverses the string stringOfDigitis;
    for (int i = stringOfDigits.length() - 1; i >= 0; --i) {
        //assigns new reversed order to int digit;
        int digit =  charToInt(stringOfDigits[i]);
        //Adds digit itelf * power
        result += digit * power;
        //changes power value to itself times itself each interaction;
        power *= base;
    }
    return result;
}
//takes an int and return its string
std::string intToDecimalString (int decimal) {
    //uses to_string() to convert from int to string
    std::string numberString = std::to_string(decimal);
    return numberString;
}

std::string intToBinaryString (int decimal) {
    //Initialises string as empty first
    std::string binary;
    /*once decimal is 0 then it has it's binary value assigned already;
        if remainder of 2 is 0, then assigns 0 to append or 1 if contrary; appends each loop keep-ing values from left to right*/
    while (decimal != 0) {
        /* repeatly divides by 2 and appends the remainder 0 or 1 to the left until decimal becomes 0*/
        binary = (decimal % 2 == 0 ? "0" : "1") + binary;
        decimal /= 2;
    }
    return binary;
}
std::string intToHexadecimalString (int decimal) {
    //Start with an array that contains all hex values and an empty string
    std::string hexString;
    const char HexDigits[] = "0123456789ABCDEF";
    //Initialises isNegative to be false first to handle negative input
    bool isNegative = false;
    //if negative, then decimal = to it's absolute value
    if (decimal < 0) {
        isNegative = true;
        decimal = -decimal;
    }
    // Once decimal reaches 0 there is no more remainder
    while (decimal > 0) {
        // remainder from 0 to 15 in each interaction;
        int remainder = decimal % 16;
        /* appends it's corresponding hexDigit fromArray and appends itself from left to right */
        hexString = HexDigits[remainder] + hexString;
        //divides decimal by 16 in each interaction;
        decimal /= 16;
    }
    //appends "-" sign whenever isNegative is true;
    if (isNegative) {
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
