public class Fractions {
    //private member variables
    private double numerator, denominator;
    double lcd, gcd;
    double quotient, remainder;
    double realnumber;

    //default constructor
    public Fractions() {
        numerator = 0;
        denominator = 1;
        lcd = 1;
        gcd = 1;
    }
    //Constructor that sets fraction (numerator and denominator)
    Fractions(double n, double d) {
        //handles if denom is negative and numerator is positive
        assert denominator != 0 : "Denominator should not be zero!";
        if (d < 0 && n > 0) {
            d *= -1;
            n *= -1;
        }
        //handles if both denom and numerator are negative
        if (d < 0 && n < 0) {
            d *= -1;
            n *= -1;
        }
        //Defines Fractions properties
        numerator = n;
        denominator = d;
        reduce();
        quotient = numerator / denominator;
        remainder = numerator % denominator;
    }
    //Computes the greatest common denominator of a fraction
    double GCD () {
        double gcd = numerator;
        double remainder = denominator;
        while (remainder != 0) {
            double temp = remainder;
            remainder = gcd % remainder;
            gcd = temp;
        }
        return gcd;
    }
    //Returns a double from a single Fraction
    private double absoluteValue() {
        realnumber = numerator / denominator;
        return realnumber;
    }
    //Returns a new fraction that is the result of rhs added into this fraction
    public Fractions add(Fractions b) {
        Fractions result = new Fractions();
        if (denominator == b.denominator) {
            result.denominator = b.denominator;
            result.numerator = numerator + b.numerator;
            double realNumber = result.absoluteValue();
        }
        if (denominator != b.denominator) {
            result.denominator = denominator * b.denominator;
            result.numerator = numerator * (b.denominator) + b.numerator * (denominator);
            result.realnumber = result.absoluteValue();
        }
        result.reduce();
        return result;
    }
    // public Fractions minus(Fractions b) {
    //     Fractions result = new Fractions()
    // }
    public void reduce() {
        gcd = GCD();
        numerator /= gcd;
        denominator /= gcd;
    }
    // Private methods used internally by Fractions class
    // computes least common denominator from 2 different fractions
    public static double LCD(double denom1, double denom2) {
        return (denom1 * denom2) / GCD(denom1, denom2);
    }
    public static double GCD(double a, double b) {
        while (b != 0) {
            double temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    //Main method
    public static void main(String[] args) {
        //Creates different Fractions for testing
        Fractions a = new Fractions(50, 55);
        Fractions b = new Fractions(49, 76);

        //Tests greatest common denominator function
        double result = a.GCD();
        System.out.println(result + "\n");

        //Tests LCD function
        // double result2 = LCD(a.denominator, b.denominator);
        // System.out.println(result2 + "\n");

        //Test + operator
        // double Fractions x = a + b;
        Fractions result3 = a.add(b);
        System.out.println(result3.realnumber + "\n");
    }
}