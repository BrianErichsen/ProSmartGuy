//
//  BitFunctions.cpp
//  BitManipulation
//
//  Created by Brian Erichsen Fagundes on 9/8/23.
//

#include "BitFunctions.hpp"
//
//  main.cpp
//  BitManipulation
//
//  Created by Brian Erichsen Fagundes on 9/7/23 and Mina Akbari
/*
  Author: Daniel Kopta, Ben Jones
  
  CS 6010
  Bit puzzles
  * The set of functions below are exercises to help you practice accessing
  * and manipulating individual bits in program data, and to help understand
  * how a computer represents numbers.
  * Replace the TODO statements below with correct implementations of the functions.
  * Each function's purpose is described in a comment.
  * main() runs many tests on your functions and reports which ones passed or failed.
  * Make sure you pass all tests.
*/

#include <iostream>
#include <fstream>
#include <vector>
using namespace std;

using std::cout;
using std::endl;
using std::string;

/*
 * Determines whether or not the specified bit is set in the input int
 *
 * Parameters:
 *   input -- The input int
 *   b -- The bit to retreive (0 = least significant, 31 = most significant)
 *
 * Returns:
 *   true if the specified bit is set, false otherwise.
 *
 * Examples:
 *   GetBit(5, 0) -> returns true, because the bit pattern for 5 is 101, we are looking at the rightmost bit
 *   GetBit(5, 1) -> returns false
 *   GetBit(-42, 31) -> returns true
 */
bool GetBit( uint32_t input, int b )
{
    /*sets mask to be 00000001 (1u) and shifts it to the left adding 0s to the right;
     if b is 1 for example; 00000001 --> 00000010 so that 1 is at b position; */
    uint32_t mask = 0x1 << b;
    /*if bit in position b is 1, then returns true; alternative code is
     bool isSet = (input & mask) == mask;*/
    bool isSet = (input & mask) != 0;
    return isSet;
}
/*
 * Determines whether or not an int is negative
 *
 * Do not use the < or > operators
 *
 * Parameters:
 *   input -- The input int
 *
 * Returns:
 *   Whether or not the int is negative
 */
bool IsNegative( int input )
{
    // moves input to right; multiply * 8
    // to convert to bits; to move int to leftmost bit or MSB position;
    int signBit = (input >> (sizeof(input) * 8 - 1)) & 1;
    //checks if input is 1;
    return signBit == 1;
}

/*
 * Determines the number of bits set (to 1) in a number
 *
 * Parameters:
 *   input -- The input int
 *
 * Returns:
 *   The number of set bits.
 *
 * Examples:
 *   NumBitsSet(5) -> returns 2
 *   NumBitsSet(64) -> returns 1
 *   NumBitsSet(-1) -> returns 32
 */
int NumBitsSet( uint32_t input ) {
    //Initialize total to be 0 first; for totals to be added to it;
    int total1s = 0;
    //int i loops through all bits positions, from 0 to 31;
    for (int i = 0; i < 32; ++i) {
        //sets LSB to 1 i times to the left; by adding 0s to right of LSB;
        // whenever bits number of input and 1 (LSB) are 1 using & operator
        // then +1 totals of 1;
        if ((input & (1 << i)) != 0) {
            total1s++;
        }
    }
    return total1s;
}

/*
 * GetByte() returns a specified byte from a 4-byte variable.
 *
 * Parameters:
 *   input -- The input value
 *   b -- The byte to retreive (0 = least significant, 3 = most significant)
 *
 * Returns:
 *   The specified byte within the input.
 *
 * Examples:
 *   GetByte( 5, 0 )          -> returns 5
 *   GetByte( 5, 1 )          -> returns 0
 *   GetByte( 0x1234abcd, 0 ) -> returns 0xcd (205 as unsigned char)
 *   GetByte( 0x1234abcd, 3 ) -> returns 0x12 (18 as an unsigned char)
 */
unsigned char GetByte( uint32_t input, int b )
{
    /*declaring new variable that holds input shifted to right//
    // by b * 8 to change from 3 to MSB, 0 to LSB*/
    uint32_t shiftedByte = (input >> (b * 8));
    //filters all bits but retains LSB 8 bits;
    unsigned char result = shiftedByte & 0xFF;
    return result;
}
/*
 * SetByte() sets the specified byte in the input to the specified value, and returns the result.
 *
 * Parameters:
 *   input -- The input value
 *   value -- The value to set the input's specified byte to
 *   b -- The byte to set (0 = least significant, 3 = most significant)
 *
 * Returns:
 *   The modified result
 *
 * Examples:
 *   SetByte( 0, 5, 0 )                -> returns 5
 *   SetByte( 0, 5, 1 )                -> returns 0x500 (1280 as unsigned int)
 *   SetByte( 0xffffffff, 0, 2 )       -> returns 0xff00ffff (4278255615 as unsigned int)
 *   SetByte( (unsigned int)-1, 0, 2 ) -> returns 0xff00ffff (4278255615 as unsigned int)
 *   SetByte( 0xabcd, 7, 1 )           -> returns 0x7cd (1997 as unsigned int)
 */
uint32_t SetByte( uint32_t input, uint8_t value, int b )
{
    /* b * 8 calculates where byte b starts;
     byte specified by b is set to 0xFF (1111 1111) and isolated to the left;
     ~flips all 0s to into 1s and all 1s into 0; bitwise operator; 0000 0000 1111 1111;
    input &= performs bitwise and operation between input and inverted mask;
    in sum here input becomes itself but specified byte by b is 0;*/
    input &= ~(0xFF << (b * 8));

    // b * 8 calculates where bytes b starts, shifts value to left to byte position;
    // input |= bitwise OR operation between input and shifted value, sets bits input to shifted value;
    input |= static_cast<uint32_t>(value) << (b * 8);
    return input;
}

/*
 * Negate() computes the negation of an integer.
 *
 * Do NOT use any of the arithmetic operators including:
 *     * or *= operators
 *     / or /= operators
 *     + or += operators
 *     - or -= operators
 *     - (unary minus operator)
 *
 * You may (and probably should) use the unary bitwise ~ operator.
 *
 * Assumes that input is not equal to INT_MIN (a bit pattern of 100...0, or the minimum integer)
 *
 * Parameters:
 *   input -- The input int
 *
 * Returns:
 *   -i (negative i)
 *
 * Examples:
 *   Negate(5) -> returns -5
 *   Negate(-1) -> returns 1
 */
int Negate( int input )
{
  // Note, it may help to do the challenge question (see below) before implementing this one...
//invert all bits using not operator;
    uint32_t negate = ~input;
    // use two's complement method by inverting bits and adding 1 into it;
    return negate + 1;
}


/*
 * Challenge Question
 *
 * Increment
 * This function should return x + 1 but should only make use of bitwise operators and == or !=
*/
int Increment( uint32_t x ){
    // find rightmost unset bit that now becomes 1;
    uint32_t rightMostUnsetBit = (~x) & (x + 1);
    //Set x's rightmost unset bit to 1;
    x |= rightMostUnsetBit;
    // Clear all bits to the right;
    x &= ~(rightMostUnsetBit - 1);
    return x;
}



/*************************************************/
/* End bit puzzles. Below are the provided tests. */
/*************************************************/


/*
 * Three forms of helper functions for the tests in main().
 * These functions compare a given value to an expected value,
 * and report an error if they don't match.
 */

/*
 * Compare two 32-bit values
 */
void Test32Bit( int a, int b, const string & message )
{
  if( a != b ) {
    cout << "FAIL: " << message << ", expected " << b << ", got " << a << endl;
  }
  else {
    cout << "PASS: " << message << endl;
  }
}

/*
 * Compare two 8-bit values
 */
void Test8Bit( unsigned char a, unsigned char b, const string & message )
{
  if( a != b ) {
    cout << "FAIL: " << message << ", expected " << std::hex << (unsigned int)b << ", got " << std::hex << (unsigned int)a << endl;
  }
  else {
    cout << "PASS: " << message << endl;
  }
}

/*
 * Compare two boolean values
 */
void TestBool( bool a, bool b, const string & message )
{
  if( a != b ) {
    cout << "FAIL: " << message << ", expected " << b << ", got " << a << endl;
  }
  else {
    cout << "PASS: " << message << endl;
  }
}
