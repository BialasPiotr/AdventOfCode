import java.io.*;
import java.util.*;

public class PartOne {

    private static int[] dx = {-1, 1, 0, 0};
    private static int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        List<String> lines = new ArrayList<>();

        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }

        char[][] garden = new char[lines.size()][lines.get(0).length()];
        for (int i = 0; i < lines.size(); i++) {
            garden[i] = lines.get(i).toCharArray();
        }

        boolean[][] visited = new boolean[garden.length][garden[0].length];
        int totalPrice = 0;

        for (int i = 0; i < garden.length; i++) {
            for (int j = 0; j < garden[0].length; j++) {
                if (!visited[i][j]) {
                    Region region = calculateRegion(garden, visited, i, j);
                    totalPrice += region.area * region.perimeter;
                }
            }
        }

        System.out.println(totalPrice);
    }

    private static Region calculateRegion(char[][] garden, boolean[][] visited, int x, int y) {
        char plantType = garden[x][y];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{x, y});
        visited[x][y] = true;

        int area = 0;
        int perimeter = 0;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            area++;

            for (int d = 0; d < 4; d++) {
                int nx = current[0] + dx[d];
                int ny = current[1] + dy[d];

                if (nx < 0 || ny < 0 || nx >= garden.length || ny >= garden[0].length || garden[nx][ny] != plantType) {
                    perimeter++;
                } else if (!visited[nx][ny]) {
                    visited[nx][ny] = true;
                    queue.add(new int[]{nx, ny});
                }
            }
        }

        return new Region(area, perimeter);
    }

    private static class Region {
        int area;
        int perimeter;

        Region(int area, int perimeter) {
            this.area = area;
            this.perimeter = perimeter;
        }
    }
}
