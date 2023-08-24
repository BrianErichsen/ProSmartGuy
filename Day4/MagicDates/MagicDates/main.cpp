//
//  main.cpp
//  MagicDates
//
//  Created by Brian Erichsen Fagundes on 8/23/23.
//

#include <iostream>
#include <string>
using namespace std;

int main() {
    string date;
    cout << "Enter a date: (mm/dd/yyyy)";
    cin >> date;
    
    string month, day, year;
    
    if (date[2] == '/') { //this code workds only if user inputs dd/mm/yyyy formart;
            month = date.substr(0, 2); //starting from the index 0 up to 2 characters;
            day = date.substr(3, 2); //starting from index 3 up to 2 characters;
            year = date.substr(6, 4); // starting from index 6 up to 4 characters;
        }
    if (date[1] == '/' && date[3] == '/') { //this code works if user inputs m/d/yyyy format;
            month = date.substr(0, 1);
            day = date.substr(2, 1);
            year = date.substr(4, 4);
        }
    if (date[2] == '/' && date[4] == '/') { //this line accounts if user input is mm/d/yyyy;
            month = date.substr(0, 2);
            day = date.substr(3, 1);
            year = date.substr(5, 4);
        }
    if (date[1] == '/' && date[4] == '/') { //this line accounts for m/dd/yyyy format;
        month = date.substr(0, 1);
        day = date.substr(2, 2);
        year = date.substr(5, 4);
    }
    string last2digitsYear = year.substr(2, 2); //here the last 2 digits of year is stored;
    
    int intMonth = stoi(month); //here string month is converted to an intenger so that we can check if each one is within normal range or not; same for each different string;
    int intDay = stoi(day);
    int intYear = stoi(year);
    int intLast2digitsYear = stoi(last2digitsYear); //here last 2 digits of year is converted into an integer;
    
    int multiplication = intMonth * intDay; //here for simplication on my if statement a am declaring a new variable which is the multiplication of int day and int month;
    
    if ((intMonth > 12 || intMonth <= 0) || (intDay > 31 || intDay <= 0) || (intYear > 9999 || intYear <= 1000)) //I put all the invalid ranges together so that the program reads invalid input whenever there is a single input out of range;
            cout << "Invalid input";
        else if (multiplication == intLast2digitsYear)
            cout << date << " IS a magic date" << endl;// this lines checks to see if date is a magic date
        else
            cout << date << " is NOT a magic date" << endl; //by elimination, everything else that has a proper range and is not a magic date is NOT a magic date;
    
    return 0;
}
