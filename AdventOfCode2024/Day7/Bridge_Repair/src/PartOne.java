import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class PartOne {
    public static void run() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        String line;
        BigInteger sum = BigInteger.ZERO;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;
            String[] parts = line.split(":");
            if (parts.length != 2) continue;
            BigInteger target = new BigInteger(parts[0].trim());
            String[] numberStrings = parts[1].trim().split("\\s+");
            List<BigInteger> numbers = new ArrayList<>();
            for (String numStr : numberStrings) {
                numbers.add(new BigInteger(numStr));
            }
            if (canReachTarget(numbers, target, 1, numbers.get(0))) {
                sum = sum.add(target);
            }
        }
        br.close();
        System.out.println(sum);
    }

    static boolean canReachTarget(List<BigInteger> numbers, BigInteger target, int index, BigInteger current) {
        if (index == numbers.size()) {
            return current.equals(target);
        }
        BigInteger nextNum = numbers.get(index);
        if (canReachTarget(numbers, target, index + 1, current.add(nextNum))) {
            return true;
        }
        if (canReachTarget(numbers, target, index + 1, current.multiply(nextNum))) {
            return true;
        }
        return false;
    }
}
