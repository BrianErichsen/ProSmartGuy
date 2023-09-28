import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class RainData {
    private String month;
    private int year;
    private double rainfall;

    public RainData(String month, int year, double rainfall) {
        this.month = month;
        this.year = year;
        this.rainfall = rainfall;
    }
    public String toString() {
        return month + " " + year + " " + rainfall;
    }
    public static void main(String[] args) {
        //Creates an array to store file's input
    ArrayList<RainData> rainfallData = new ArrayList<>();

    File file = new File ("/Users/brianerichsenfagundes/Desktop/Atlanta.txt");

    //Starts with try and finishes with catch // Ensures that scanner closes
    try (Scanner sc = new Scanner(file)) {
        //Reads the first line of file and stores the name of the city (first line)
        String cityName = sc.nextLine();
        System.out.println ("City Name: " + cityName);
        //Loops and continues until are more lines to read
        while (sc.hasNextLine()) {
            //stores line variable
            String line = sc.nextLine();
            //Splits the line into array of parts based on space
            String[] parts = line.split("\t");
            String[] dateParts = parts[0].trim().split(" ");
            //if array has 3 elements
            if (dateParts.length >= 2) {
                //assigns month for first element
                String month = dateParts[0];
                System.out.println("Month " + month);
                //Assigns second element into year and convert it to a int
                String yearStr = dateParts[1];
                System.out.println("Year " + yearStr);
                int year = Integer.parseInt(yearStr);
                if (parts.length >= 3) {
                //Assigns third element to rainfall and convert it to double
                double rainfall = Double.parseDouble(parts[2].trim());
                System.out.println("Rainfall " + rainfall);
                //new object that extracts month, year, and rainfall and
                //stores the data
                RainData rainData = new RainData(month, year, rainfall);
                rainfallData.add(rainData);
                } else {
                    System.err.println("Incomplete data in line: " + line);
                }
            }
        }
        //prints error msg if file was not found
    } catch(FileNotFoundException e) {
        System.err.println("File not found: " + e.getMessage());
    }
    // for (int i = 0; i < rainfallData.size(); i++) {
    //     System.out.println(rainfallData.get(i));
    // }
    }
}
