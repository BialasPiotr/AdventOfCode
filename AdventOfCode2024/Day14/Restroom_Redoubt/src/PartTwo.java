import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PartTwo {
    public static void run() throws IOException {
        Scanner s = new Scanner(new File("input.txt"));
        List<int[]> robots = new ArrayList<>();
        while (s.hasNextLine()) {
            String line = s.nextLine().trim();
            line = line.replace("p=", "").replace("v=", "");
            String[] parts = line.split("\\s+");
            String[] p = parts[0].split(",");
            String[] v = parts[1].split(",");
            int px = Integer.parseInt(p[0]);
            int py = Integer.parseInt(p[1]);
            int vx = Integer.parseInt(v[0]);
            int vy = Integer.parseInt(v[1]);
            robots.add(new int[]{px, py, vx, vy});
        }
        int bestT = 0;
        long minArea = Long.MAX_VALUE;
        int consecutiveUp = 0;
        for (int t = 0; t < 200000; t++) {
            int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE;
            int minY = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE;
            for (int[] r : robots) {
                int x = r[0] + t * r[2];
                int y = r[1] + t * r[3];
                if (x < minX) minX = x;
                if (x > maxX) maxX = x;
                if (y < minY) minY = y;
                if (y > maxY) maxY = y;
            }
            long w = (long) (maxX - minX);
            long h = (long) (maxY - minY);
            long area = w * h;
            if (area < minArea) {
                minArea = area;
                bestT = t;
                consecutiveUp = 0;
            } else {
                consecutiveUp++;
            }
            if (consecutiveUp > 10000) break;
        }
        System.out.println(bestT);
    }
}
