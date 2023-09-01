//
//  main.cpp
//  CommandLineArgs
//
//  Created by Brian Erichsen Fagundes on 9/1/23.
//

#include <iostream>

int main(int argc, const char * argv[]) {
    // insert code here...
    std::cout << "Hello, World!\n";
    std::cout << argc << std::endl;
    std::cout << &argv[0] << std::endl;
    std::cout << *argv[1] << " " << *argv[2] << std::endl;
    std::cout << argv[1] << " " << argv[2] << std::endl;
    
    return 0;
}
