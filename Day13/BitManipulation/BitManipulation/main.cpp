

/*************************************************/
/* End bit puzzles. Below are the provided tests. */
/*************************************************/


/*
 * Three forms of helper functions for the tests in main().
 * These functions compare a given value to an expected value,
 * and report an error if they don't match.
 */
#include <iostream>
#include <fstream>
#include <vector>
#include "BitFunctions.hpp"
using namespace std;

using std::cout;
using std::endl;
using std::string;


int main()
{
  /*
   * These tests exercise your bit puzzle solutions.
   * Many of the tests rely on hexadecimal instead of decimal.
   * Since hexadecimal maps much more easily to binary, it's  more useful
   * when we want to specify a number with a specific bit pattern,
   * such as alternating 10101010... (0xaa), or 01010101... (0x55),
   * or all ones in a certain byte: 0x00ff0000
   */
  
//  TestBool( GetBit( 0, 0 ), false, "GetBit1" );
//  TestBool( GetBit( 0, 1 ), false, "GetBit2" );
//  TestBool( GetBit( 0, 31 ), false, "GetBit3" );
//  TestBool( GetBit( -1, 0 ), true, "GetBit4" );
//  TestBool( GetBit( -1, 1 ), true, "GetBit5" );
//  TestBool( GetBit( -1, 31 ), true, "GetBit6" );
//  TestBool( GetBit( 0xAAAAAAAA, 0 ), false, "GetBit7" );
//  TestBool( GetBit( 0xAAAAAAAA, 16 ), false, "GetBit8" );
//  TestBool( GetBit( 0xAAAAAAAA, 17 ), true, "GetBit9" );
//  TestBool( GetBit( 0xAAAAAAAA, 31 ), true, "GetBit10" );
    cout << GetBit(-1, 31) << endl;
    cout << IsNegative(0xAAAAAAAA) << endl;
    cout << INT_MIN << endl;
    cout << NumBitsSet(-1) << endl;
    cout << hex << static_cast<int>(GetByte(0xAAAAAAAA, 0)) << endl;
    cout << SetByte(0, 0xFF, 0) << endl;
    cout << Negate(5) << endl;
    cout << dec << Increment(-999) << endl;


//  Test8Bit( GetByte( 0x12345678, 3 ), 0x12, "GetByte11" );
//
//  Test32Bit( SetByte( 0, 0xFF, 0 ), 0xFF, "SetByte1" );
//  Test32Bit( SetByte( 0, 0xFF, 1 ), 0xFF00, "SetByte2" );
//  Test32Bit( SetByte( 0, 0xFF, 2 ), 0xFF0000, "SetByte3" );
//  Test32Bit( SetByte( 0, 0xFF, 3 ), 0xFF000000, "SetByte4" );
//  Test32Bit( SetByte( 0x12345678, 0xAA, 0 ), 0x123456aa, "SetByte5" );
//  Test32Bit( SetByte( 0x12345678, 0xAA, 1 ), 0x1234aa78, "SetByte6" );
//  Test32Bit( SetByte( 0x12345678, 0xAA, 2 ), 0x12aa5678, "SetByte7" );
//  Test32Bit( SetByte( 0x12345678, 0xAA, 3 ), 0xaa345678, "SetByte8" );
//
//  Test32Bit( Negate( -1 ), 1, "Negate1" );
//  Test32Bit( Negate( 1 ), -1, "Negate2" );
//  Test32Bit( Negate( 2 ), -2, "Negate3" );
//  Test32Bit( Negate( -2 ), 2, "Negate4" );
//  Test32Bit( Negate( 0 ), 0, "Negate5" );
//  Test32Bit( Negate( 0x7fffffff ), 0x80000001, "Negate6" );
//  Test32Bit( Negate( 0xAAAAAAAA ), 0x55555556, "Negate7" );
//
//
//  Test32Bit( Increment( 0 ), 1, "Increment1" );
//  Test32Bit( Increment( -1 ), 0, "Increment2" );
//  Test32Bit( Increment( 10000 ), 10001, "Increment3" );
//  Test32Bit( Increment( -999 ), -998, "Increment4" );

  return 0;
}

