import java.io.*;
import java.util.*;

public class PartOne {
    public static void run() throws IOException {
        List<String> grid = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        String line;
        int startX = -1, startY = -1;
        while ((line = br.readLine()) != null) {
            grid.add(line);
            if (startX == -1) {
                int idx = line.indexOf('^');
                if (idx != -1) {
                    startX = grid.size() - 1;
                    startY = idx;
                }
            }
        }
        br.close();
        if (startX == -1) {
            System.out.println(0);
            return;
        }
        int[][] directions = { {-1,0}, {0,1}, {1,0}, {0,-1} };
        int dir = 0; // 0: up, 1: right, 2: down, 3: left
        int x = startX, y = startY;
        Set<String> visited = new HashSet<>();
        while (true) {
            visited.add(x + "," + y);
            int nx = x + directions[dir][0];
            int ny = y + directions[dir][1];
            if (nx < 0 || nx >= grid.size() || ny < 0 || ny >= grid.get(0).length()) {
                break;
            }
            if (grid.get(nx).charAt(ny) == '#') {
                dir = (dir + 1) % 4;
            } else {
                x = nx;
                y = ny;
            }
        }
        System.out.println(visited.size());
    }
}
