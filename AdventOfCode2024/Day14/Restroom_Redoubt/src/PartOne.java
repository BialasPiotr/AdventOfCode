import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PartOne {
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
        for (int i = 0; i < 100; i++) {
            for (int[] r : robots) {
                r[0] = ((r[0] + r[2]) % 101 + 101) % 101;
                r[1] = ((r[1] + r[3]) % 103 + 103) % 103;
            }
        }
        int q1 = 0, q2 = 0, q3 = 0, q4 = 0;
        for (int[] r : robots) {
            if (r[0] < 50 && r[1] < 51) q1++;
            else if (r[0] > 50 && r[1] < 51) q2++;
            else if (r[0] < 50 && r[1] > 51) q3++;
            else if (r[0] > 50 && r[1] > 51) q4++;
        }
        System.out.println((long) q1 * q2 * q3 * q4);
    }
}
