

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
    TestBool( GetBit( 0, 0 ), false, "GetBit1" );
    TestBool( GetBit( 0, 1 ), false, "GetBit2" );
    TestBool( GetBit( 0, 31 ), false, "GetBit3" );
    TestBool( GetBit( -1, 0 ), true, "GetBit4" );
    TestBool( GetBit( -1, 1 ), true, "GetBit5" );
    TestBool( GetBit( -1, 31 ), true, "GetBit6" );
    TestBool( GetBit( 0xAAAAAAAA, 0 ), false, "GetBit7" );
    TestBool( GetBit( 0xAAAAAAAA, 16 ), false, "GetBit8" );
    TestBool( GetBit( 0xAAAAAAAA, 17 ), true, "GetBit9" );
    TestBool( GetBit( 0xAAAAAAAA, 31 ), true, "GetBit10" );
    TestBool( IsNegative( 0 ), false, "IsNegative1" );
    TestBool( IsNegative( 1 ), false, "IsNegative2" );
    TestBool( IsNegative( -1 ), true, "IsNegative3" );
    TestBool( IsNegative( 0xAAAAAAAA ), true, "IsNegative4" );
    TestBool( IsNegative( 0x55555555 ), false, "IsNegative5" );
    TestBool( IsNegative( INT_MIN ), true, "IsNegative6" );
    TestBool( IsNegative( INT_MAX ), false, "IsNegative7" );
    Test32Bit( NumBitsSet( 0 ), 0, "NumBitsSet1" );
    Test32Bit( NumBitsSet( 1 ), 1, "NumBitsSet2" );
    Test32Bit( NumBitsSet( 2 ), 1, "NumBitsSet3" );
    Test32Bit( NumBitsSet( 3 ), 2, "NumBitsSet4" );
    Test32Bit( NumBitsSet( 5 ), 2, "NumBitsSet5" );
    Test32Bit( NumBitsSet( -1 ), 32, "NumBitsSet6" );
    Test32Bit( NumBitsSet( 0xAAAAAAAA ), 16, "NumBitsSet7" );
    Test32Bit( NumBitsSet( 0x55555555 ), 16, "NumBitsSet8" );
    Test8Bit( GetByte( 0, 0 ), 0, "GetByte1" );
    Test8Bit( GetByte( 0, 1 ), 0, "GetByte2" );
    Test8Bit( GetByte( 0, 2 ), 0, "GetByte3" );
    Test8Bit( GetByte( 0, 3 ), 0, "GetByte4" );
    Test8Bit( GetByte( 0xAAAAAAAA, 0 ), 0xaa, "GetByte4" );
    Test8Bit( GetByte( 0xAAAAAAAA, 1 ), 0xaa, "GetByte5" );
    Test8Bit( GetByte( 0xAAAAAAAA, 2 ), 0xaa, "GetByte6" );
    Test8Bit( GetByte( 0xAAAAAAAA, 3 ), 0xaa, "GetByte7" );
    Test8Bit( GetByte( 0x12345678, 0 ), 0x78, "GetByte8" );
    Test8Bit( GetByte( 0x12345678, 1 ), 0x56, "GetByte9" );
    Test8Bit( GetByte( 0x12345678, 2 ), 0x34, "GetByte10" );
    Test8Bit( GetByte( 0x12345678, 3 ), 0x12, "GetByte11" );
    Test32Bit( SetByte( 0, 0xFF, 0 ), 0xFF, "SetByte1" );
    Test32Bit( SetByte( 0, 0xFF, 1 ), 0xFF00, "SetByte2" );
    Test32Bit( SetByte( 0, 0xFF, 2 ), 0xFF0000, "SetByte3" );
    Test32Bit( SetByte( 0, 0xFF, 3 ), 0xFF000000, "SetByte4" );
    Test32Bit( SetByte( 0x12345678, 0xAA, 0 ), 0x123456aa, "SetByte5" );
    Test32Bit( SetByte( 0x12345678, 0xAA, 1 ), 0x1234aa78, "SetByte6" );
    Test32Bit( SetByte( 0x12345678, 0xAA, 2 ), 0x12aa5678, "SetByte7" );
    Test32Bit( SetByte( 0x12345678, 0xAA, 3 ), 0xaa345678, "SetByte8" );
    Test32Bit( Negate( -1 ), 1, "Negate1" );
    Test32Bit( Negate( 1 ), -1, "Negate2" );
    Test32Bit( Negate( 2 ), -2, "Negate3" );
    Test32Bit( Negate( -2 ), 2, "Negate4" );
    Test32Bit( Negate( 0 ), 0, "Negate5" );
    Test32Bit( Negate( 0x7fffffff ), 0x80000001, "Negate6" );
    Test32Bit( Negate( 0xAAAAAAAA ), 0x55555556, "Negate7" );
    Test32Bit( Increment( 0 ), 1, "Increment1" );
    Test32Bit( Increment( -1 ), 0, "Increment2" );
    Test32Bit( Increment( 10000 ), 10001, "Increment3" );
    Test32Bit( Increment( -999 ), -998, "Increment4" );
  

  return 0;
}

