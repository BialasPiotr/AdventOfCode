import java.io.*;
import java.util.*;

public class PartTwo {
    public static void run() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("input2.txt"));
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
            if (ants.size() < 2) continue;

            for (int[] a : ants) antinodes.add(a[1] + " " + a[0]);

            for (int i = 0; i < ants.size(); i++) {
                for (int j = i + 1; j < ants.size(); j++) {
                    int x1 = ants.get(i)[0], y1 = ants.get(i)[1], x2 = ants.get(j)[0], y2 = ants.get(j)[1];
                    int dx = x2 - x1, dy = y2 - y1;
                    int g = gcd(Math.abs(dx), Math.abs(dy));
                    dx /= g;
                    dy /= g;

                    int tx = x1, ty = y1;
                    // go backwards
                    while (tx >= 0 && tx < w && ty >= 0 && ty < h) {
                        antinodes.add(ty + " " + tx);
                        tx -= dx; ty -= dy;
                    }

                    // go forwards
                    tx = x1 + dx; ty = y1 + dy;
                    while (tx >= 0 && tx < w && ty >= 0 && ty < h) {
                        antinodes.add(ty + " " + tx);
                        tx += dx; ty += dy;
                    }
                }
            }
        }

        System.out.println(antinodes.size());
    }

    static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
