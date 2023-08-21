//
//  main.cpp
//  HelloWorld
//
//  Created by Brian Erichsen Fagundes on 8/21/23.
//

#include <iostream>

using namespace std;

int main ()
{
    cout << "Hello World, this is what is called Fibonacci numbers" << endl;
    int a = 0;
    int b = 1;
    int c = a + b;
    for (int k = 0; k <= 10; k++) {
        a = b;
        b = c;
        c = a + b;
        cout << c << endl;
    }
    
    return 0;
    
}
