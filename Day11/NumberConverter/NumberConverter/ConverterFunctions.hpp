//
//  ConverterFunctions.hpp
//  NumberConverter
//
//  Created by Brian Erichsen Fagundes on 9/5/23.
//

#ifndef ConverterFunctions_hpp
#define ConverterFunctions_hpp

#include <stdio.h>
#include <string>
int charToInt (char z);
int stringToInt(std::string stringOfDigits, int base);
std::string intToDecimalString (int decimal);
std::string intToBinaryString (int decimal);
std::string intToHexadecimalString (int decimal);
void testFunctions();
#endif /* ConverterFunctions_hpp */
