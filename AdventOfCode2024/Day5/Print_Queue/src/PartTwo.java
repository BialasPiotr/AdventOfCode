import java.io.*;
import java.util.*;

public class PartTwo {
    public static void run() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input2.txt"));
        String line;
        List<int[]> rules = new ArrayList<>();
        List<List<Integer>> updates = new ArrayList<>();

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
            if (!valid) {
                Map<Integer, List<Integer>> adj = new HashMap<>();
                Map<Integer, Integer> inDegree = new HashMap<>();
                for (int page : pages) {
                    adj.put(page, new ArrayList<>());
                    inDegree.put(page, 0);
                }
                for (int[] rule : rules) {
                    int x = rule[0];
                    int y = rule[1];
                    if (pages.contains(x) && pages.contains(y)) {
                        adj.get(x).add(y);
                        inDegree.put(y, inDegree.get(y) + 1);
                    }
                }


                Queue<Integer> queue = new LinkedList<>();
                for (int page : pages) {
                    if (inDegree.get(page) == 0) {
                        queue.add(page);
                    }
                }

                List<Integer> sorted = new ArrayList<>();
                while (!queue.isEmpty()) {
                    int current = queue.poll();
                    sorted.add(current);
                    for (int neighbor : adj.get(current)) {
                        inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                        if (inDegree.get(neighbor) == 0) {
                            queue.add(neighbor);
                        }
                    }
                }
                if (sorted.size() != pages.size()) {
                    continue;
                }

                int middle = sorted.size() / 2;
                if (sorted.size() % 2 == 0) {
                    middle = middle - 1;
                }
                sum += sorted.get(middle);
            }
        }
        System.out.println(sum);
    }
}
