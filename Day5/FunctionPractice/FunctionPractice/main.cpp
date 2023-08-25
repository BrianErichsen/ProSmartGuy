//
//  main.cpp
//  FunctionPractice
//
//  Created by Brian Erichsen Fagundes on 8/24/23.
//

#include <iostream>
#include <cmath>
#include <ctime>
#include <cctype>

using namespace std;
// Beginning of part 2;
// using doubles for values of hypotenuse and simple variables names as a, b, and c;
double hypotenuse (double a, double b) {
    double c = pow(a * a + b * b, 0.5);
    return c;
}
// using a bool type since I want to return whether first character is either capital or not;
bool isCapitalized (string d) {
    char e = d[0];
    if (isupper(e)) {
        return true;
    }
    return false;
}
string boolToString (bool f) {
    return f ? "true" : "false"; //using the conditional operator for simplication
} // instead of 2 if statements;

// this is the challenge function that tests an integer whether or not its is prime;
bool isPrime (int g) {
    if (g <= 1) {
        return false; // 0 and 1 are not prime numbers;
    }
    for (int h = 2; h <= g / 2; h++) {
        if (g % h == 0) {
            return false; // loops tries to find a divisor, if finds it; it is not
            // prime; the way to check whether a number is prime or not by dividing by
            //2 to up to it's half hence why h <= g / 2;
        }
    }
    return true; // return true if no divisor found; number is prime;
}
                   
int main() {
    double sideA, sideB, sideC;
    cout << "Enter side a (height) and side b (base) from a right sided triangle: ";
    cin >> sideA >> sideB;
    // pow function (pow(a, b) returns a^b
    // I prefer pow instead of sqrt(x); its personal preference but I could do
    // sideC = squrt(sideA * sideA + sideB * sideB) and it would give me same result;
    sideC = pow((sideA * sideA + sideB * sideB), 0.5);
    cout << sideC << endl;
    cout << hypotenuse(sideA, sideB) << endl; // I am just testing my function here;
    
    // beginning of part B;
    double speed, angle;
    cout << "Enter speed you are going and the angle that you are going: ";
    cin >> speed >> angle;
    double const PI = 3.14159; //declaring PI as a double const;
    // angle * PI / 180 so that the angle is converted into radians;
    double x_velocity = speed * cos(angle * PI / 180);
    double y_velocity = speed * sin(angle * PI / 180);
    
    cout << "X velocity is " << x_velocity << " and y velocity is " <<
    y_velocity << endl;
    // beginning of part C; this code works
    // here the ctime lybrary is being called to tell the asctime and the
    // time function as well being called to receive an input in secods ~~ i think
    // I still learning about time(0) function and how to use it;
    std::time_t result = std::time(nullptr);
      std::cout << std::asctime(std::localtime(&result))
                << result << " seconds since the Epoch\n";
    //here I am testing my isCapitalized function along with the boolToString function;
    string pp;
    cout << "Enter a string: ";
    cin >> pp;
    bool iAmTired = isCapitalized(pp);
    cout << boolToString(iAmTired) << endl;
    
    //testing my isPrime function;
    int num;
    cout << "Enter a number: ";
    cin >> num;
    cout << isPrime(num) << endl;
    
    return 0;
}
