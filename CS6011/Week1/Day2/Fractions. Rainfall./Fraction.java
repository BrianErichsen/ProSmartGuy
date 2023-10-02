//Created by Brian Erichsen Fagundes

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Fraction implements Comparable<Fraction> {
    //private member variables
    private long numerator, denominator;
    long lcd, gcd;
    long quotient, remainder;
    //this one takes care of long toDouble()
    double realnumber;

    //default constructor
    public Fraction() {
        numerator = 0;
        denominator = 1;
        lcd = 1;
        gcd = 1;
    }
    //Constructor that sets fraction (numerator and denominator)
    Fraction(long n, long d) {
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
    long GCD () {
        long gcd = numerator;
        long remainder = denominator;
        while (remainder != 0) {
            long temp = remainder;
            remainder = gcd % remainder;
            gcd = temp;
        }
        return gcd;
    }
    //Returns a long from a single Fraction
    private double getTheRealNumber() {
        double numeratorDouble = numerator;
        realnumber = numeratorDouble / denominator;
        return realnumber;
    }
    public int compareTo(Fraction otherFraction) {
        //Calculates the cross product of fractions for comparison
        long crossProduct1 = this.numerator * otherFraction.denominator;
        long crossProduct2 = otherFraction.numerator * this.denominator;

        return Long.compare(crossProduct1, crossProduct2);
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
    public static long LCD(long denom1, long denom2) {
        return (denom1 * denom2) / GCD(denom1, denom2);
    }
    public static long GCD(long a, long b) {
        while (b != 0) {
            long temp = b;
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
        //Creates different Fraction for testing
        Fraction a = new Fraction(50, 55);
        Fraction b = new Fraction(49, 76);

        //Tests greatest common denominator function
        long result = a.GCD();
        System.out.println(result + "\n");

        //Tests LCD function
        // long result2 = LCD(a.denominator, b.denominator);
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
    }

    //Main method
    public static void main(String[] args) {

        List<Fraction> fraction = new ArrayList<>();
        fraction.add(new Fraction(3, 4));
        fraction.add(new Fraction(1, 2));
        fraction.add(new Fraction (2, 3));

        //Sort the list of fractions in ascending order
        Collections.sort(fraction);
        //Prints the sorted list
        for (Fraction fractions : fraction) {
            System.out.println(fractions.realnumber);
        }
        //For testing my exception statement
        test();
    }
}