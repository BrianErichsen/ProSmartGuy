import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static void main(String[] args) {
        String inputFile = "traceroute_output_after_3_hrs.txt";
        String outputFile = "average_delay_after_3_hrs.txt";

        //read all lines into a created list
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // BufferedWriter for writing output
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            //process each line in the list
            for (String line : lines) {
                String ipAddress = extractIpAddress(line);
                double averageDelay = computeAverageDelay(line, lines);
                writer.write(ipAddress + " " + averageDelay + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Extracts IP address
    private static String extractIpAddress(String line) {
        //initalize index of the ()
        int startIndex = line.indexOf('(');
        int endIndex = line.indexOf(')', startIndex);
        //if there is () in the line, extract the substring between ()
        if (startIndex != -1 && endIndex != -1) {
            return line.substring(startIndex + 1, endIndex);
        }
        //else if there is no ip address, return a blank line
        return "";
    }

    //Calculates average delay
    private static double computeAverageDelay(String line, List<String> lines) {
        //slit the line by white space and store in an array of strings
        String[] tokens = line.split("\\s+");
        double sum = 0;
        int count = 0;

        //iterate through all the strings in the line and find the marker ms
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            if (token.contains("ms")) {
                try {
                    //extract value and update sum and count
                    double value = Double.parseDouble(tokens[i - 1]);
                    sum += value;
                    count++;
                } catch (NumberFormatException ignored) {
                }
            } else {
                //if the token does not contain 'ms', check the next line for continuation
                String nextLine = getNextLine(lines, i);
                if (nextLine != null) {
                    line += " " + nextLine; // concatenate the next line to the current line
                }
            }
        }
        double average = count > 0 ? sum / count : 0;
        return average;
    }

    //function to get next line
    private static String getNextLine(List<String> lines, int currentIndex) {
        //check if there is a next line
        if (currentIndex + 1 < lines.size()) {
            return lines.get(currentIndex + 1);
        }
        return null;
    }
}