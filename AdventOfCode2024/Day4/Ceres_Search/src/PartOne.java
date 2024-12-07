import java.io.*;
import java.util.*;

public class PartOne {
    public static void run() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) lines.add(line);
        br.close();
        int n = lines.size();
        int m = lines.get(0).length();
        char[][] grid = new char[n][m];
        for (int i=0;i<n;i++) grid[i]=lines.get(i).toCharArray();
        int count=0;
        String word="XMAS";
        int len=word.length();
        int[][] dirs={{1,0},{-1,0},{0,1},{0,-1},{1,1},{1,-1},{-1,1},{-1,-1}};
        for (int r=0;r<n;r++){
            for (int c=0;c<m;c++){
                if (grid[r][c]==word.charAt(0)){
                    for (int[] d:dirs){
                        int rr=r,cc=c;
                        int k=0;
                        for (;k<len;k++){
                            if (rr<0||rr>=n||cc<0||cc>=m||grid[rr][cc]!=word.charAt(k)) break;
                            rr+=d[0];
                            cc+=d[1];
                        }
                        if (k==len) count++;
                    }
                }
            }
        }
        System.out.println(count);
    }
}
