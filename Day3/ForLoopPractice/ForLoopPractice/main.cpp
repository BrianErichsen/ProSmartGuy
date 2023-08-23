//
//  main.cpp
//  ForLoopPractice
//
//  Created by Brian Erichsen Fagundes on 8/22/23.
//

#include <iostream>
using namespace std;

int main() {
    
    for (int i = 1; i <= 10; i++) { //in this case the for loop is more appropriate since I am looping a known number of times while while loops are more appropriate for an unknow amount of times
        cout << i << " ";
    }
    cout << endl;
    
    int k = 1;
    while (k <= 10) {
        cout << k << " ";
        k++;
    }
    cout << endl;
    
    int num1, num2;
    cout << "Enter 2 numbers";
    cin >> num1 >> num2;
    
    if (num1 > num2) { //using a temporary number solves the issue of when the user inputs a higher number first and a smaller number for num2. In that case temp has to be higher number, 1; and num1 = num2 and num2 = temp number (I came up with this solution by writing these variables in a piece of paper so I could visualize how to solve it
        int temp = num1;
        num1 = num2;
        num2 = temp;
    }
    
    while (num1 <= num2) { // this loop prints the numbers betwewen num1 and num2 until num1 meets num2; using a while loop because num1 and num
        cout << num1 << " ";
        num1++;
    }
    cout << endl;
    //this next line of code prints all odd numbers between 1 and 20; solutuion 1
    for (int c = 1; c <= 20; c += 2) {
        cout << c << " ";
    }
    cout << endl; // to keep output organized
    
    //this next line of code is solution 2 for printing out odd numbers from 1 to 20
    int d = 1;
    while (d <= 20) {
        if (d % 2 != 0)
            cout << d << " ";
        d++; // I did not like this approach because it took more work to find the odd numbers, this approach includes a if statement but I think without it is more clean and less work to do. I knew that d % 2 == 0 is all even numbers so I just inverted that to get the odd numbers
    }
    cout << endl;
    
    int z = 0; //declaring z initially to be z so that it does not count for the negative number whenever the last number is negative
    int sum = 0;
    do { //I had to use a do loop because I tested first with a just while loop and the while lopp was accounting for the negative number in the sum, here the do loop does the code first before checking the condition
        sum = sum + z;
        cout << "Enter a number: ";
        cin >> z;
    }
    while (z >= 0);
    cout << sum << " " << endl;

    
    cout << "Multiplication Table\n"; // this line of code just organizes things a bit better for the multiplication table
    cout << "-----------------------\n";
    
    for (int a = 1; a <= 5; a++) { // here a has to increase by increments of 1, and first loop takes care of the collumn of the table from 1 to 5
        cout << a << "x*: ";
        for (int b = 1; b <= 5; b++) { // here the vertical of the table is taken care where it prints a * b which is the multiplication tabble
            cout << (a * b) << " ";
        }
        cout << endl;
    }

    return 0;
}
