import java.util.Scanner;
import java.io.File;

public class PartOne {
    public static void run() throws Exception {
        Scanner scanner = new Scanner(new File("input.txt"));
        int safeReports = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;
            String[] tokens = line.split("\\s+");
            int[] levels = new int[tokens.length];
            for (int i = 0; i < tokens.length; i++) {
                levels[i] = Integer.parseInt(tokens[i]);
            }
            if (levels.length < 2) continue;
            boolean isIncreasing = true;
            boolean isDecreasing = true;
            boolean differencesInRange = true;
            for (int i = 0; i < levels.length - 1; i++) {
                int diff = levels[i + 1] - levels[i];
                if (diff == 0) {
                    isIncreasing = false;
                    isDecreasing = false;
                } else if (diff > 0) {
                    isDecreasing = false;
                } else {
                    isIncreasing = false;
                }
                int absDiff = Math.abs(diff);
                if (absDiff < 1 || absDiff > 3) {
                    differencesInRange = false;
                }
            }
            if (differencesInRange && (isIncreasing || isDecreasing)) {
                safeReports++;
            }
        }
        System.out.println("Number of safe reports: " + safeReports);
    }
}
