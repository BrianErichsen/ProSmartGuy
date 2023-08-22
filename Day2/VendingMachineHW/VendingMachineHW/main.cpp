//
//  main.cpp
//  VendingMachineHW
//
//  Created by Brian Erichsen Fagundes on 8/22/23.
//

#include <iostream>

using namespace std; // makes my life easier so don't have to copy std multiple times

int main() {
    
    int itemPrice, amountPaid;
    
    cout << "Enter item price in cents: ";
    cin >> itemPrice;
    
    cout << "Enter amount paid in cents: ";
    cin >> amountPaid;
    
    int change = amountPaid - itemPrice;
    int quarters = change / 25.0;
    int remainder = change % 25; //keeps track of leftover after program computed quarters
    int dimes = remainder / 10;
    remainder = remainder % 10; //keeps track of change after dimes
    int nickels = remainder / 5;
    remainder = remainder % 5; //keeps track of change after nickels
    int pennies = remainder;
    
    cout << change << " cents" << endl;
    cout << "Quarters: " << quarters << endl;
    cout << "Dimes: " << dimes << endl;
    cout << "Nickels: " << nickels << endl;
    cout << "Pennies: " << pennies << endl;
    
    return 0;
}
