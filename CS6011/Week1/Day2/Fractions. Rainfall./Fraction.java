public class Fraction {
    //private member variables
    private double numerator, denominator;
    double lcd, gcd;
    double quotient, remainder;
    //this one takes care of double toDouble()
    double realnumber;

    //default constructor
    public Fraction() {
        numerator = 0;
        denominator = 1;
        lcd = 1;
        gcd = 1;
    }
    //Constructor that sets fraction (numerator and denominator)
    Fraction(double n, double d) {
        //handles if denom is negative and numerator is positive
        assert denominator != 0 : "Denominator should not be zero!";
        if (d == 0) {
            throw new IllegalArgumentException("Denominator should not be zero");
        }
        if (d < 0 && n > 0) {
            d *= -1;
            n *= -1;
        }
        //handles if both denom and numerator are negative
        if (d < 0 && n < 0) {
            d *= -1;
            n *= -1;
        }
        //Defines Fraction properties
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
    private double getTheRealNumber() {
        realnumber = numerator / denominator;
        return realnumber;
    }

    /*Operators --------------------------------------------------------------*/
    //Returns a new fraction that is the result of rhs added into this fraction
    public Fraction plus(Fraction b) {
        //when equal denom the sum is itself for denom
        //for nume is simply adding one to eachother
        Fraction result = new Fraction();
        if (denominator == b.denominator) {
            result.denominator = b.denominator;
            result.numerator = numerator + b.numerator;
            double realNumber = result.getTheRealNumber();
        }
        //When different denom is itself * rhs (b)
        //Nume is (itself_denom * rhs_denom) + (rhs_nume * itself_denom)
        if (denominator != b.denominator) {
            result.denominator = denominator * b.denominator;
            result.numerator = numerator * (b.denominator) + b.numerator * (denominator);
            result.realnumber = result.getTheRealNumber();
        }
        //simplifies fraction and returns result
        result.reduce();
        return result;
    }
    //Still working on this

    // //equal operator && constructor
    // public Fraction copy(Fraction b) {
    //     //Creates new copy
    //     Fraction result = new Fraction();
    //     if ...

        // numerator = b.numerator;
        // denominator = b.denominator;
        // realnumber = b.realnumber;
        // gcd = b.gcd;

    // }
    //minus operator
    public Fraction minus(Fraction b) {
            Fraction result = new Fraction();
        if (denominator == b.denominator) {
            result.denominator = b.denominator;
            result.numerator = numerator - b.numerator;
            double realNumber = result.getTheRealNumber();
        }
        //When different denom is itself * rhs (b)
        //Nume is (itself_denom * rhs_denom) + (rhs_nume * itself_denom)
        if (denominator != b.denominator) {
            result.denominator = denominator * b.denominator;
            result.numerator = numerator * (b.denominator) - b.numerator * (denominator);
            double realnumber = result.getTheRealNumber();
        }
        //simplifies fraction and returns result
        result.reduce();
        return result;
    }
    //Times operator
    public Fraction times(Fraction rhs) {
        Fraction result = new Fraction();
        result.numerator = numerator * rhs.numerator;
        result.denominator = denominator * rhs.denominator;
        result.realnumber = result.getTheRealNumber();
        result.reduce();
        return result;
    }
    public Fraction dividedBy(Fraction rhs) {
        Fraction result = new Fraction();
        //New Reversed rhs fraction
        Fraction rhsInverse = new Fraction(rhs.denominator, rhs.numerator);
        //same as multiplying lhs to reciprocal of rhs
        result.numerator = numerator * rhsInverse.numerator;
        result.denominator = denominator * rhsInverse.denominator;
        result.realnumber = result.getTheRealNumber();
        //Simplifies result and returns it
        result.reduce();
        return result;
    }
    private Fraction Reciprocal() {
        Fraction reciprocal = new Fraction(numerator, denominator);
        reciprocal.numerator = denominator;
        reciprocal.denominator = numerator;
        reciprocal.realnumber = reciprocal.getTheRealNumber();
        return reciprocal;
    }
    public void reduce() {
        gcd = GCD();
        numerator /= gcd;
        denominator /= gcd;
    }
    // Private methods used internally by Fraction class
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
    public static void test() {
        try {
            Fraction c = new Fraction(50, 0);
        } catch (IllegalArgumentException e) {
            System.out.println("Denominator should not be zero");
        }
    }

    //Main method
    public static void main(String[] args) {
        //Creates different Fraction for testing
        Fraction a = new Fraction(50.0, 55.0);
        Fraction b = new Fraction(49.0, 76.0);

        //Tests greatest common denominator function
        double result = a.GCD();
        System.out.println(result + "\n");

        //Tests LCD function
        // double result2 = LCD(a.denominator, b.denominator);
        // System.out.println(result2 + "\n");

        //Test + operator
        Fraction result3 = a.plus(b);
        System.out.println(result3.realnumber);
        //Test - operator
        Fraction result4 = a.minus(b);
        System.out.println(result4.realnumber);
        System.out.println(result4);
        //Test * operator
        Fraction result5 = a.times(b);
        System.out.println(result5.realnumber);
        //Test reciprocal operator
        Fraction result7 = a.Reciprocal();
        System.out.println(result7.realnumber);
        // Test / operator
        Fraction result6 = a.dividedBy(b);
        System.out.println(result6.realnumber);
        //For testing my exception statement
        test();
    }
}