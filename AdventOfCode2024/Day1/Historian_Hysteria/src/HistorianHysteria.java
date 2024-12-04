import java.io.*;
import java.util.*;

public class HistorianHysteria {
    public static void main(String[] args) {
        List<Integer> leftList = new ArrayList<>();
        List<Integer> rightList = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split("\\s+");
                if (parts.length != 2) {
                    continue;
                }
                int leftNum = Integer.parseInt(parts[0]);
                int rightNum = Integer.parseInt(parts[1]);

                leftList.add(leftNum);
                rightList.add(rightNum);
            }

            reader.close();

            if (leftList.size() != rightList.size()) {
                return;
            }

            Collections.sort(leftList);
            Collections.sort(rightList);

            long totalDistance = 0;
            for (int i = 0; i < leftList.size(); i++) {
                int distance = Math.abs(leftList.get(i) - rightList.get(i));
                totalDistance += distance;
            }

            System.out.println("Total distance: " + totalDistance);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
