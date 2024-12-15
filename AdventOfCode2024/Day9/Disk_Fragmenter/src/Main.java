import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static class Block {
        Integer id;
        int size;
        Block(Integer i, int s) {
            id = i;
            size = s;
        }
    }

    static List<Block> parse_input(String text) {
        List<Integer> digits = new ArrayList<>();
        for (char c : text.toCharArray()) {
            if (Character.isDigit(c)) digits.add(c - '0');
        }
        List<Block> r = new ArrayList<>();
        int k = 0;
        for (int i = 0; i < digits.size(); i++) {
            if (i % 2 == 0) {
                r.add(new Block(k, digits.get(i)));
                k++;
            } else {
                r.add(new Block(null, digits.get(i)));
            }
        }
        return r;
    }

    static int free_pos(List<Block> data) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).id == null) return i;
        }
        return -1;
    }

    static boolean done(List<Block> data) {
        int p = free_pos(data);
        if (p == -1) return true;
        for (int i = p; i < data.size(); i++) {
            if (data.get(i).id != null) return false;
        }
        return true;
    }

    static Block pop_file(List<Block> data) {
        for (int i = data.size() - 1; i >= 0; i--) {
            if (data.get(i).id != null) {
                Block b = data.remove(i);
                return b;
            }
        }
        return new Block(-1, -1);
    }

    static long p1(String text) {
        List<Block> data = parse_input(text);
        Block lf = pop_file(data);
        while (!done(data)) {
            int fsi = free_pos(data);
            int diff = lf.size - data.get(fsi).size;
            if (diff > 0) {
                data.set(fsi, new Block(lf.id, data.get(fsi).size));
                lf = new Block(lf.id, diff);
                data.add(new Block(null, data.get(fsi).size));
            } else if (diff < 0) {
                data.set(fsi, new Block(lf.id, lf.size));
                data.add(new Block(null, lf.size));
                lf = pop_file(data);
                data.add(fsi + 1, new Block(null, Math.abs(diff)));
            } else {
                data.set(fsi, new Block(lf.id, lf.size));
                data.add(new Block(null, lf.size));
                lf = pop_file(data);
            }
        }
        data.add(free_pos(data), lf);
        long c = 0, vpos = 0;
        for (Block b : data) {
            if (b.id == null) break;
            for (long j = vpos; j < vpos + b.size; j++) {
                c += b.id * j;
            }
            vpos += b.size;
        }
        return c;
    }

    static int max_id(List<Block> data) {
        int m = -1;
        for (Block b : data) {
            if (b.id != null && b.id > m) m = b.id;
        }
        return m;
    }

    static int find_pos(List<Block> data, int id) {
        for (int i = 0; i < data.size(); i++) {
            Block b = data.get(i);
            if (b.id != null && b.id == id) return i;
        }
        return -1;
    }

    static boolean fits(Block f, Block fs) {
        return fs.size - f.size >= 0;
    }

    static void subst(List<Block> data, int l_pos, int fsi) {
        Block f = data.get(l_pos);
        Block fs = data.get(fsi);
        int diff = f.size - fs.size;
        if (diff < 0) {
            data.set(fsi, new Block(f.id, f.size));
            data.set(l_pos, new Block(null, data.get(l_pos).size));
            data.add(fsi + 1, new Block(null, Math.abs(diff)));
        } else if (diff == 0) {
            data.set(fsi, new Block(f.id, f.size));
            data.set(l_pos, new Block(null, data.get(l_pos).size));
        }
    }

    static long p2(String text) {
        List<Block> data = parse_input(text);
        int l_id = max_id(data);
        int l_pos = find_pos(data, l_id);
        while (l_id > 0) {
            for (int i = 0; i < l_pos; i++) {
                Block b = data.get(i);
                if (b.id == null && fits(data.get(l_pos), b)) {
                    subst(data, l_pos, i);
                    break;
                }
            }
            l_id -= 1;
            l_pos = find_pos(data, l_id);
        }
        long c = 0, vpos = 0;
        for (Block b : data) {
            if (b.id != null) {
                for (long j = vpos; j < vpos + b.size; j++) {
                    c += b.id * j;
                }
            }
            vpos += b.size;
        }
        return c;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        String t = sb.toString();
        System.out.println(p1(t));
        System.out.println(p2(t));
    }
}
