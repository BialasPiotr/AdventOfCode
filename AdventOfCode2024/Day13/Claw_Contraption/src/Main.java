import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        ArrayList<Machine> machines = new ArrayList<>();
        String line;
        ArrayList<String> lines = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (!line.isEmpty()) lines.add(line);
        }
        for (int i = 0; i < lines.size(); i += 3) {
            String aLine = lines.get(i);
            String bLine = lines.get(i+1);
            String pLine = lines.get(i+2);
            int[] a = parseButton(aLine);
            int[] b = parseButton(bLine);
            int[] p = parsePrize(pLine);
            machines.add(new Machine(a[0],a[1],b[0],b[1],p[0],p[1]));
        }
        int totalCost = 0;
        int count = 0;
        for (Machine m: machines) {
            int cost = solveMachine(m);
            if (cost >= 0) {
                count++;
                totalCost += cost;
            }
        }
        System.out.println(totalCost);
    }

    static int solveMachine(Machine m) {
        int best = -1;
        for (int nA=0;nA<=100;nA++){
            int Xrem = m.Px - m.Ax*nA;
            int Yrem = m.Py - m.Ay*nA;
            if (Xrem >= 0 && Yrem >= 0) {
                if (m.Bx != 0 && m.By != 0) {
                    if (Xrem % m.Bx == 0 && Yrem % m.By == 0) {
                        int nBx = Xrem/m.Bx;
                        int nBy = Yrem/m.By;
                        if (nBx == nBy && nBx>=0 && nBx<=100) {
                            int cost=3*nA+nBx;
                            if (best<0 || cost<best) best=cost;
                        }
                    }
                }
            }
        }
        return best;
    }

    static int[] parseButton(String s) {
        s = s.substring(s.indexOf("X+"));
        String[] parts = s.split(",");
        int x = Integer.parseInt(parts[0].replace("X+","").replace("X=","").trim());
        int y = Integer.parseInt(parts[1].replace("Y+","").replace("Y=","").trim());
        return new int[]{x,y};
    }

    static int[] parsePrize(String s) {
        s = s.substring(s.indexOf("X="));
        String[] parts = s.split(",");
        int x = Integer.parseInt(parts[0].replace("X=","").trim());
        int y = Integer.parseInt(parts[1].replace("Y=","").trim());
        return new int[]{x,y};
    }
}

class Machine {
    int Ax,Ay,Bx,By,Px,Py;
    Machine(int Ax,int Ay,int Bx,int By,int Px,int Py){
        this.Ax=Ax;this.Ay=Ay;this.Bx=Bx;this.By=By;this.Px=Px;this.Py=Py;
    }
}
