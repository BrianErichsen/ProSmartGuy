//
//  main.cpp
//  Road Trip Calculator
//
//  Created by Brian Erichsen Fagundes on 8/21/23.
//

#include <iostream>
#include <iomanip>
using namespace std;

int main() {
    int distanceInMiles, milesPerGallon;
    double dollarsPerGallon;
    
    cout << "Enter distance traveled in miles: ";
    cin >> distanceInMiles;
    
    cout << "Enter the vehicle's miles per gallon (MPG) efficiency: ";
    cin >> milesPerGallon;
    
    cout << "Enter the cost of gas in dollars per gallon: ";
    cin >> dollarsPerGallon;
    
    double gallonsPerTrip = distanceInMiles / static_cast<double>(milesPerGallon); // using static_cast to make the division accurate
    double costOfTrip = gallonsPerTrip * dollarsPerGallon;
    
    cout << "The total cost of the trip in dollars is $ " << fixed << setprecision(2) << costOfTrip << endl;
    
    return 0;
}
