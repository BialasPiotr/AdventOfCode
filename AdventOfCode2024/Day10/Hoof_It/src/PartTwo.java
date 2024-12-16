import java.io.*;
import java.util.*;

public class PartTwo {
    public static void run() throws IOException {
        List<String> mapData = readInput("input2.txt");
        int[][] topographicMap = parseMap(mapData);
        int sumOfRatings = calculateTrailheadRatings(topographicMap);
        System.out.println("Sum of ratings of all trailheads: " + sumOfRatings);
    }

    private static List<String> readInput(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line.trim());
            }
        }
        return lines;
    }

    private static int[][] parseMap(List<String> mapData) {
        int rows = mapData.size();
        int cols = mapData.get(0).length();
        int[][] map = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                map[i][j] = mapData.get(i).charAt(j) - '0';
            }
        }
        return map;
    }

    private static int calculateTrailheadRatings(int[][] map) {
        int rows = map.length;
        int cols = map[0].length;
        int totalRating = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (map[i][j] == 0) {
                    totalRating += calculateTrailheadRating(map, i, j);
                }
            }
        }
        return totalRating;
    }

    private static int calculateTrailheadRating(int[][] map, int startX, int startY) {
        int rows = map.length;
        int cols = map[0].length;
        boolean[][] visited = new boolean[rows][cols];
        return dfs(map, visited, startX, startY, 0);
    }

    private static int dfs(int[][] map, boolean[][] visited, int x, int y, int currentHeight) {
        int rows = map.length;
        int cols = map[0].length;

        if (x < 0 || y < 0 || x >= rows || y >= cols || visited[x][y] || map[x][y] != currentHeight) {
            return 0;
        }

        visited[x][y] = true;
        int trails = 0;

        if (map[x][y] == 9) {
            trails++;
        }

        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        for (int i = 0; i < 4; i++) {
            trails += dfs(map, visited, x + dx[i], y + dy[i], currentHeight + 1);
        }

        visited[x][y] = false;
        return trails;
    }
}
