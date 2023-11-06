
//by Brian Erichsen Fagundes
//answers for lab questions

/*Where is the assertEquals method defined? What does it do? Can we use assertEquals for more than just int types? (Hint: Consult the API for JUnit)
        Assert equals is used to compare two data values and see if they are equal. It is defined on import static org.junit.Assert.assertEquals library. It can be used for any data types pretty much.


        Briefly describe what each of the three tests is checking.
        Each of these 3 tests is checking 3 different inputs such as checking the output of the method when the array is empty for array 1; all equal elements for array 2 and small Random array elements for array 3; so basically testing the result from 3 different types of input in the method.
        Why is our unimplemented findSmallestDiff method passing one of the tests?
        Because since arraty; it passes the test due to not having any elements and automatically returning 0;y 1 is emp


        Why are we failing the third test? How should our method be modified to correct this?
        We are failing test 3 because … the int diff was accounting for negative number; I changed that value to be equal to Math.abs(then what it was) and now it passed all tests the way is supposed to be.
        What is the appropriate call to assertEquals for your new test (test 4)?
        Is an accurate prediction ; for example for array {1, 2, 3, 4, 5, 6, 7, 8, 9, 10} the assert prediction would be -1;
        Provide one more test (test 5) for the findSmallestDiff method. Briefly describe your test and write the call to assertEquals here.
        I created a new array where it increments it’s value by 2; from 1 to 9 like 1, 3, 5, 7, 9 … and tested for smallestDiff to be -2 and it was correct;
        Briefly describe your unit tests for Assignment 1.
        For assignment 1; I will add more tests such as that all cases and paths for unevaluated parts may be covered. I will make sure that I setup properly and update it properly the SetUp and tearDown methods as well. */

public class Main {
    private int[] arr1, arr2, arr3;

    public static void main(String[] args) {

    }//End of main bracket


    /**
     * @param  -- input array of integers
     * @return The smallest difference (absolute value of subtraction) among every
     *         pair of integers in the input array. If the array contains less
     *         than two items, returns -1.
     */
    public static int findSmallestDiff(int[] a) {
        if (a.length < 2) {
            return -1;
        }

        int diff = a[0] - a[1];

        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                int tmp_diff = Math.abs(a[i] - a[j]);

                if (tmp_diff < diff)
                    diff = tmp_diff;
            }
        }

        return diff;
    }
}// end of class bracket