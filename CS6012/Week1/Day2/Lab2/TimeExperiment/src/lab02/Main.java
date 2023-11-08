package lab02;
//Answers to questions

/*On your computer, how many times per second does the millisecond timer update?
        1- On my computer, my Mac; the millisecond timer updates about 1000 times per second;
        however the actual precision can be influenced by many things like system load; hardware and power settings.

        2- Is it possible to determine how many times per second the nanosecond timer updates? If so, how many? If not, why not?
        To determine how many times per second the nanosecond timer updates can be challenging in a given operating
        system due to the precision of timers depending on hardware capabilities, system load, timer APIs and timer resolution.
        In theory yes it is possible but very hard to achieve due to the conditions mentioned.

        3- Judging by experiment 4, how long does it appear to take to compute System.nanoTime()?
        At least on my mac; something about 30 to 80 nanoseconds or somewhere less than 1000 nanoseconds.

        4- Estimate the precision of your answer above (+/- how many nanoseconds?)
        Maybe +/- 40 nanoseconds or even more

        5- How long does it take to compute the square root of the numbers 1 through 10?
        For my mac, it took 13084 nanoseconds; but I also lost some time by printing out the end time - start time.

        6- Estimate the precision of your answer above (+/- how many nanoseconds?)
        Probably the precision is about +/- 5000 nanoseconds; since I run the program multiple times and got very different times each time.

        7- If you repeat the square root test 100x as many times, does the precision improve?
        18250 first time I run it by running 100x my test; 18335 the second time and 1800 the third time; Yes.
        The precision does improve since there isn’t such a huge spike on the output.

        8- How could you improve the results (get a more accurate estimate of elapsed time)?
        I did some research about this and found that java has some specialized benchmarking tools such as Java Microbenchmarking Harness that takes care of many factors that affects timing accuracy and provides more reliable results.*/

//by Brian Erichsen Fagundes
public class Main {

    public static void calculateSquareRoots() {
        long startTime = System.nanoTime();
        for (int j = 1; j <= 100; j++) {
            for (int i = 1; i <= 10; i++) {
                double squareRoot = Math.sqrt(i);
            }
        }
        long endTime = System.nanoTime();
        System.out.println(endTime - startTime);
    }
    public static void main(String[] args) {
    calculateSquareRoots();
    }//end of main bracket
}//end of class bracket