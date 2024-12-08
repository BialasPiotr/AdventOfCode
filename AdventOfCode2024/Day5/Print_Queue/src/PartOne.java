import java.io.*;
import java.util.*;

public class PartOne {
    public static void run() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        String line;
        List<int[]> rules = new ArrayList<>();
        List<List<Integer>> updates = new ArrayList<>();
        boolean readingRules = true;

        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;
            if (line.contains("|")) {
                String[] parts = line.split("\\|");
                if (parts.length != 2) continue;
                int x = Integer.parseInt(parts[0].trim());
                int y = Integer.parseInt(parts[1].trim());
                rules.add(new int[]{x, y});
            } else if (line.contains(",")) {
                String[] parts = line.split(",");
                List<Integer> update = new ArrayList<>();
                for (String part : parts) {
                    update.add(Integer.parseInt(part.trim()));
                }
                updates.add(update);
            }
        }
        br.close();

        long sum = 0;
        for (List<Integer> update : updates) {
            Set<Integer> pages = new HashSet<>(update);
            Map<Integer, Integer> indexMap = new HashMap<>();
            for (int i = 0; i < update.size(); i++) {
                indexMap.put(update.get(i), i);
            }
            boolean valid = true;
            for (int[] rule : rules) {
                int x = rule[0];
                int y = rule[1];
                if (pages.contains(x) && pages.contains(y)) {
                    if (indexMap.get(x) >= indexMap.get(y)) {
                        valid = false;
                        break;
                    }
                }
            }
            if (valid) {
                int middle = update.size() / 2;
                if (update.size() % 2 == 0) {
                    middle = middle - 1;
                }
                sum += update.get(middle);
            }
        }
        System.out.println(sum);
    }
}
