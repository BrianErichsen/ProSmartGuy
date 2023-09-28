import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class RainData {
    private String month;
    private int year;
    private double rainfall;

    //Constructor to initialize RainData object
    public RainData(String month, int year, double rainfall) {
        this.month = month;
        this.year = year;
        this.rainfall = rainfall;
    }
    //Method to get month
    public String getMonth() {
        return month;
    }
    //Method to get rainfall
    public double getRainfall() {
        return rainfall;
    }
    //Calculate the average of a list of values
    private static double calculteAverage(List<Double> values) {
        double sum = 0;
        for (Double value : values) {
            sum += value;
        }
        return sum / values.size();
    } //Static means it only can be accessed within RainData class
    //Calculates monthly average for a list of RainData objects
    //Hash data stores data based on keys
    private static Map<String, List<Double>> calculateMonthlyAverages(List<RainData> data) {
        //Creates new map based on month keys
        Map<String, List<Double>> monthlyAverages = new HashMap<>();
        //Iterates through data and assigns month and rainfall
        for (RainData rainData : data) {
            String month = rainData.getMonth();
            double rainfall = rainData.getRainfall();
            //Checks if map contains the month key, if not, add it
            //Accumulates rainfall data for each individual month
        if (!monthlyAverages.containsKey(month)) {
            monthlyAverages.put(month, new ArrayList<>());
        }
        //access ArrayList associated with specific month and add the value of rainfall
        monthlyAverages.get(month).add(rainfall);
    }
    return monthlyAverages;
    }
    //Write result to target file; takes filename and a Map as parameters
    private static void writeResultsToFile(String filename, Map<String, List<Double>> monthlyAverages) {
        //try means a block that have an exception
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            //Do iterations through the keys months; for each month does ...
            for (String month : monthlyAverages.keySet()) {
                //takes list of rainfall values for current month and computes average
                double average = calculteAverage(monthlyAverages.get(month));
                //prints out average for each month
                writer.println("Month: " + month + ", Average Rainfall: " + average);
            }
            //Throws an exeption if a IOException happens when dealing with files opening or closing
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        // Create a list to store RainData objects
        List<RainData> rainfallData = new ArrayList<>();
        // "/Users/brianerichsenfagundes/Desktop/Atlanta.txt"
        String filename = "rainfall_data.txt";

        //Throws an exception  if file is not found
        try {
            // Create a Scanner to read the data file
            Scanner sc = new Scanner(new File(filename));
            //loops over all file until file has no more lines
            while (sc.hasNextLine()) {
                // Read the current line and trims any trailing whitespaces
                String line = sc.nextLine().trim();
                // Split the line into array of parts based on one or more spaces
                String[] parts = line.split("\\s+");//whenever one or more spaces

                if (parts.length >= 3) {
                    //Extracts data from last part of string array
                    // Check if the last part can be parsed as a double (rainfall)
                    //If rainfallStr has a double and converts string double to double
                    String rainfallStr = parts[parts.length - 1].trim();
                    if (rainfallStr.matches("\\d+\\.\\d+")) {
                        double rainfall = Double.parseDouble(rainfallStr);

                        // Extract month and year from the remaining parts
                        String month = parts[0];
                        int year = Integer.parseInt(parts[1]);
                        // Create a RainData object and add it to the list
                        RainData rainData = new RainData(month, year, rainfall);
                        //Finally adding RainData object data to rainfallData
                        rainfallData.add(rainData);
                    } else {
                        System.err.println("Invalid rainfall data in line: " + line);
                    }
                } else {
                    System.err.println("Incomplete data in line: " + line);
                }
            }
            //calculates monthly averages
            Map<String, List<Double>> monthlyAverages = calculateMonthlyAverages(rainfallData);
            //Specify the file output name
            String outputFilename = "rainfall_results.txt";
            //Call the write method
            writeResultsToFile(outputFilename, monthlyAverages);

            // Close the Scanner
            sc.close();
            
            // Print the extracted data
            // for (RainData data : rainfallData) {
            //     System.out.println(data.rainfall);
            // }

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
        }
    }
}

