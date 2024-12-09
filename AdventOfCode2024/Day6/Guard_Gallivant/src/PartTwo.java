import java.io.*;
import java.util.*;

public class PartTwo {
    public static void run() throws IOException {
        List<String> grid = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("input2.txt"));
        String line;
        int startX = -1, startY = -1, dir = -1;
        while ((line = br.readLine()) != null) {
            grid.add(line);
            if (startX == -1) {
                int idx = line.indexOf('^');
                if (idx != -1) {
                    startX = grid.size() - 1;
                    startY = idx;
                    dir = 0;
                } else {
                    idx = line.indexOf('>');
                    if (idx != -1) {
                        startX = grid.size() - 1;
                        startY = idx;
                        dir = 1;
                    } else {
                        idx = line.indexOf('v');
                        if (idx != -1) {
                            startX = grid.size() - 1;
                            startY = idx;
                            dir = 2;
                        } else {
                            idx = line.indexOf('<');
                            if (idx != -1) {
                                startX = grid.size() - 1;
                                startY = idx;
                                dir = 3;
                            }
                        }
                    }
                }
            }
        }
        br.close();
        if (startX == -1) {
            System.out.println(0);
            return;
        }
        int[][] directions = { {-1,0}, {0,1}, {1,0}, {0,-1} };
        int count = 0;
        for(int i = 0; i < grid.size(); i++) {
            for(int j = 0; j < grid.get(i).length(); j++) {
                if(grid.get(i).charAt(j) == '.' && !(i == startX && j == startY)) {
                    StringBuilder sb = new StringBuilder(grid.get(i));
                    sb.setCharAt(j, '#');
                    String[] modifiedGrid = grid.toArray(new String[0]);
                    modifiedGrid[i] = sb.toString();
                    Set<String> visited = new HashSet<>();
                    int x = startX, y = startY, currentDir = dir;
                    boolean loop = false;
                    while(true) {
                        String state = x + "," + y + "," + currentDir;
                        if(visited.contains(state)) {
                            loop = true;
                            break;
                        }
                        visited.add(state);
                        int nx = x + directions[currentDir][0];
                        int ny = y + directions[currentDir][1];
                        if(nx < 0 || nx >= modifiedGrid.length || ny < 0 || ny >= modifiedGrid[0].length()) {
                            break;
                        }
                        if(modifiedGrid[nx].charAt(ny) == '#') {
                            currentDir = (currentDir + 1) % 4;
                        }
                        else {
                            x = nx;
                            y = ny;
                        }
                    }
                    if(loop) {
                        count++;
                    }
                }
            }
        }
        System.out.println(count);
    }
}
