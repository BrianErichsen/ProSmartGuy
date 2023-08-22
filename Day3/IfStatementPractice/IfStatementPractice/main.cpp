//  Group name: Brian Erichsen Fagundes, I am doing this alone because I usually do my labs a day in advance but I will help others during lab hours.
//  main.cpp
//  IfStatementPractice
//
//  Created by Brian Erichsen Fagundes on 8/22/23.
//

#include <iostream>
using namespace std;

int main() {
    
    int age;
    cout << "Please enter your age: ";
    cin >> age; //stores variable age from input
    
    if (age >= 30) { // using { for multiple statements that are separated from the next if statement, note how next set of if does not need {} note how 30 is checked first before 18 so we don't have issues with the output
        cout << "You are not only old enough to vote but also old enough to run for senate\n";
    }
    else if (age >= 18) {
        cout << "You are old enough to vote/n";
    }
    if (age >= 80) //started new set of if statements to check which generation user is in
        cout << "Hey old person, you are part of the greatest generation\n";
        else if (age >= 60)
            cout << "You are not part of the greatest generation but you are a baby boomer, ok bommer?\n";
        else if (age >= 40)
            cout << "You are part of the generation X\n";
        else if (age >= 20)
            cout << "You are part of the millennial generation\n";
        else if (age >= 0)
            cout << "You are part of the iKid generation\n";
        else // tell the user that if a negative input is inserted that that input is invalid
            cout << "Invalid input, plese enter an appropriate age\n";
        
    return 0;
}
