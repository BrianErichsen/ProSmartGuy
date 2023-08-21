//
//  main.cpp
//  VolumeConvert
//
//  Created by Brian Erichsen Fagundes on 8/21/23.
//

#include <iostream>

using namespace std;

int main() {
    
    int ounces;
    cout << "Enter volume in ounces: ";
    cin >> ounces;
    
    double cups = ounces / 8.0;
    double pints = ounces / 16.0;
    double gallons = ounces / 128.0;
    double litters = ounces * 0.0296;
    double cubicInches = ounces * 1.8;
    
    cout << "Ounces: " << ounces << endl;
    cout << "Cups: " << cups << endl;
    cout << "Pints: " << pints << endl;
    cout << "Gallons: " << gallons << endl;
    cout << "Litters: " << litters << endl;
    cout << "CubicInches: " << cubicInches << endl;
    
    return 0;
}
