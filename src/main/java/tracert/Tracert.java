package tracert;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO
 * @author: Wei Liang
 * @date: 2023年04月26日 6:58 PM
 */
public class Tracert {
    public static void main(String[] args) throws IOException {
        String tcpdump = "E:\\CSCI4760\\src\\main\\java\\tracert\\sampletcpdump.txt";
        String analysis = "analysis.txt";

        BufferedReader inputFile = new BufferedReader(new FileReader(tcpdump));
        FileWriter outputFile = new FileWriter(analysis);
        List<String> lines = new ArrayList<>();

        String line = inputFile.readLine();
        while (line != null) {
            lines.add(line);
            line = inputFile.readLine();
        }

        int placeHolder = 0;
        int writer = 0;

        for (String curr : lines) {
            String id = null;
            String ttl = null;
            String time_1 = null;
            String time_2 = null;

            if (curr.contains("id")) {
                id = curr.substring(curr.indexOf("id"), curr.indexOf(", o")).trim();
                ttl = curr.substring(curr.indexOf("ttl"), curr.indexOf(", i")).trim();
                time_1 = curr.substring(0, curr.indexOf("IP")).trim();
            }

            for (int i = placeHolder + 1; i < lines.size(); i++) {
                String compare = lines.get(i);
                if (compare.contains("id")) {
                    String check = compare.substring(compare.indexOf("id"), compare.indexOf(", o")).trim();
                    if (check.equals(id) && !check.equals("id 0")) {
                        String ip_line = lines.get(i - 1);
                        String IP = ip_line.substring(0, ip_line.indexOf(" >")).trim();
                        String timeLine = lines.get(i - 2);
                        time_2 = timeLine.substring(0, timeLine.indexOf("IP")).trim();
                        double time_difference = Math.round((Double.parseDouble(time_2) - Double.parseDouble(time_1)) * 1000 * 1000) / 1000.0;
                        if (writer == 0) {
                            outputFile.write(ttl.toUpperCase() + "\n");
                            outputFile.write(IP + "\n");
                            outputFile.write(time_difference + " ms\n");
                            writer++;
                        } else if (writer % 3 == 0 && writer != 0) {
                            outputFile.write(ttl.toUpperCase() + "\n");
                            outputFile.write(IP + "\n");
                            outputFile.write(time_difference + " ms\n");
                            writer++;
                        } else {
                            outputFile.write(time_difference + " ms\n");
                            writer++;
                        }
                    }
                }
            }
            placeHolder++;
        }

        inputFile.close();
        outputFile.close();

    }
}
