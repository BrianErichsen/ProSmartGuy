import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//calculates total round trip delay by each packet
//then calculate average delay per those 200 packets
public class Ping_parser {
    public static void main(String[] args) {
        String input = "ping_output.txt";
        try {
            BufferedReader rd = new BufferedReader(new FileReader(input));
            //round trip time // creates list for storing all rrt times from all packets
            List<Double> rtt = new ArrayList<>();
            //reads the first line
            rd.readLine();
            String line;

            while ((line = rd.readLine()) != null) {
                String[] parts = line.split("time=");
                if (parts.length > 1) {
                    //  \s 1 white space and \s+ one or more white spaces
                    double delay = Double.parseDouble(parts[1].split("\\s+")[0]);
                    rtt.add(delay);
                }
            }
            double averageQueuingDelay = get_avg_queuing_delay(rtt);
            System.out.println(averageQueuingDelay + " ms");

            rd.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//end of main bracket

    static public double get_avg_queuing_delay(List<Double> rtt) {
        //min value when sorted is index 0
        Collections.sort(rtt);
        double minVal = rtt.get(0);

        List<Double> queueDelays = new ArrayList<>();
        for (double delay : rtt) {
            queueDelays.add(delay - minVal);
        }
        double sum = 0;
        for (double queueDelay : queueDelays) {
            sum += queueDelay;
        }
        return sum / queueDelays.size();
    }
}//end of class bracket
