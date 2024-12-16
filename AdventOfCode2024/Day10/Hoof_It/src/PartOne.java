import java.io.*;
import java.util.*;

public class PartOne {
    public static void run() throws IOException {
        List<String> mapData = readInput("input.txt");
        int[][] topographicMap = parseMap(mapData);
        int sumOfScores = calculateTrailheadScores(topographicMap);
        System.out.println("Sum of scores of all trailheads: " + sumOfScores);
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

    private static int calculateTrailheadScores(int[][] map) {
        int rows = map.length;
        int cols = map[0].length;
        int totalScore = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (map[i][j] == 0) {
                    totalScore += calculateTrailheadScore(map, i, j);
                }
            }
        }
        return totalScore;
    }

    private static int calculateTrailheadScore(int[][] map, int startX, int startY) {
        int rows = map.length;
        int cols = map[0].length;
        boolean[][] visited = new boolean[rows][cols];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startX, startY});
        visited[startX][startY] = true;
        int score = 0;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];

            if (map[x][y] == 9) {
                score++;
                continue;
            }

            int[] dx = {-1, 1, 0, 0};
            int[] dy = {0, 0, -1, 1};
            for (int i = 0; i < 4; i++) {
                int newX = x + dx[i];
                int newY = y + dy[i];

                if (isValidStep(map, visited, x, y, newX, newY)) {
                    visited[newX][newY] = true;
                    queue.add(new int[]{newX, newY});
                }
            }
        }
        return score;
    }

    private static boolean isValidStep(int[][] map, boolean[][] visited, int currentX, int currentY, int newX, int newY) {
        int rows = map.length;
        int cols = map[0].length;
        if (newX < 0 || newY < 0 || newX >= rows || newY >= cols) return false;
        if (visited[newX][newY]) return false;
        return map[newX][newY] == map[currentX][currentY] + 1;
    }
}
