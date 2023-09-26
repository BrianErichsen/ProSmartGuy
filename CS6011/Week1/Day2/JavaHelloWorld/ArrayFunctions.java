public class ArrayFunctions {
    public static void main(String[] args) {
        int[] numbers = new int[5];
        //creates array

        //assigns each array index to a value
        numbers[0] = 1;
        numbers[1] = 2;
        numbers[2] = 3;
        numbers[3] = 4;
        numbers[4] = 5;

        //creates a sum to be printed out after loop
        int sum = 0;
        //Prints each element of the array and add it to sum
        for (int i = 0; i < numbers.length; i++) {
            System.out.println(numbers[i] + " \n");
            sum += numbers[i];
        }
        //Prints out Array's sum
        System.out.println(sum);
    }
}
