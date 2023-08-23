//
//  main.cpp
//  DateFormats
//
//  Created by Brian Erichsen Fagundes on 8/23/23.
//

#include <iostream>
#include <string>
#include <cstdlib>
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
    int intMonth = stoi(month); //here string month is converted to an intenger so that we can check if each one is within normal range or not; same for each different string;
    int intDay = stoi(day);
    int intYear = stoi(year);
    
    if (month == "01" || month == "1") //I kept month as string so that I can change it to a new string that corresponds to the month;
        month = "January";
        else if (month == "02" || month == "2")
            month = "February";
        else if (month == "03" || month == "2")
            month = "March";
        else if (month == "04" || month == "4")
            month = "April";
        else if (month == "05" || month == "5")
            month = "May";
        else if (month == "06" || month == "6")
            month = "June";
        else if (month == "07" || month == "7")
            month = "July";
        else if (month == "08" || month == "8")
            month = "August";
        else if (month == "09" || month == "9")
            month = "September";
        else if (month == "10")
            month = "October";
        else if (month == "11")
            month = "November";
        else if (month == "12")
            month = "December";
    
    if ((intMonth > 12 || intMonth <= 0) || (intDay > 31 || intDay <= 0) || (intYear > 9999 || intYear <= 1000)) //I put all the invalid ranges together so that the program reads invalid input whenever there is a single input out of range;
        cout << "Invalid input";
    else // if all input is within range, then this lines is excecuted;
        cout << month << " " << intDay << ", " << intYear << endl;

    return 0;
}
