import java.io.*;
import java.util.*;

public class PartTwo {
    public static void run() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("input2.txt"));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) lines.add(line);
        br.close();
        int n = lines.size();
        int m = lines.get(0).length();
        char[][] grid = new char[n][m];
        for (int i=0;i<n;i++) grid[i]=lines.get(i).toCharArray();
        int count=0;
        for (int r=1; r<n-1; r++){
            for (int c=1; c<m-1; c++){
                if (grid[r][c]=='A') {
                    char ul=(r-1>=0&&c-1>=0)?grid[r-1][c-1]:' ';
                    char ur=(r-1>=0&&c+1<m)?grid[r-1][c+1]:' ';
                    char dl=(r+1<n&&c-1>=0)?grid[r+1][c-1]:' ';
                    char dr=(r+1<n&&c+1<m)?grid[r+1][c+1]:' ';
                    boolean diag1_1=(ul=='M'&&dr=='S')||(ul=='S'&&dr=='M');
                    boolean diag1_2=(dr=='M'&&ul=='S')||(dr=='S'&&ul=='M');
                    boolean diag2_1=(ur=='M'&&dl=='S')||(ur=='S'&&dl=='M');
                    boolean diag2_2=(dl=='M'&&ur=='S')||(dl=='S'&&ur=='M');
                    if ((diag1_1||diag1_2)&&(diag2_1||diag2_2)) count++;
                }
            }
        }
        System.out.println(count);
    }
}
