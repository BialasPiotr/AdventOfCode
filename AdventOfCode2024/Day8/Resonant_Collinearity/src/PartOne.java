import java.io.*;
import java.util.*;

public class PartOne {
    public static void run() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) lines.add(line);
        br.close();

        int h = lines.size();
        int w = 0;
        for (String s : lines) if (s.length() > w) w = s.length();
        char[][] map = new char[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (j < lines.get(i).length()) map[i][j] = lines.get(i).charAt(j);
                else map[i][j] = '.';
            }
        }

        Map<Character, List<int[]>> freqMap = new HashMap<>();
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                char c = map[y][x];
                if (c != '.') {
                    freqMap.putIfAbsent(c, new ArrayList<>());
                    freqMap.get(c).add(new int[]{x, y});
                }
            }
        }

        Set<String> antinodes = new HashSet<>();
        for (Map.Entry<Character, List<int[]>> e : freqMap.entrySet()) {
            List<int[]> ants = e.getValue();
            for (int i = 0; i < ants.size(); i++) {
                for (int j = i + 1; j < ants.size(); j++) {
                    int xA = ants.get(i)[0], yA = ants.get(i)[1];
                    int xB = ants.get(j)[0], yB = ants.get(j)[1];
                    int x1 = 2 * xA - xB;
                    int y1 = 2 * yA - yB;
                    int x2 = 2 * xB - xA;
                    int y2 = 2 * yB - yA;
                    if (x1 >= 0 && x1 < w && y1 >= 0 && y1 < h) antinodes.add(y1 + " " + x1);
                    if (x2 >= 0 && x2 < w && y2 >= 0 && y2 < h) antinodes.add(y2 + " " + x2);
                }
            }
        }

        System.out.println(antinodes.size());
    }
}
