import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day6{

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        System.out.println("--- Advent of Code 2025: Day 6 ---");
        System.out.println("Select part of the solution:");
        System.out.println("1 - Part 1");
        System.out.println("2 - Part 2");
        System.out.print("Your choice: ");

        String choice = scanner.nextLine();
        String filename = "";

        if (choice.equals("1")) {
            filename = "Resources/input1.txt";
        } else if (choice.equals("2")) {
            filename = "Resources/input2.txt";
        } else {
            System.out.println("Invalid choice");
            scanner.close();
            return;
        }

        List<String> rawLines = readRawInput(filename);

        if (rawLines.isEmpty()) {
            System.out.println("No solution found");
        } else {
            if (choice.equals("1")) {
                solvePart1(rawLines);
            } else {
                solvePart2(rawLines);
            }
        }
        scanner.close();
    }

    private static void solvePart1(List<String> rawLines) {
        int maxWidth = 0;
        for (String line : rawLines) {
            maxWidth = Math.max(maxWidth, line.length());
        }
        BigInteger grandTotal = BigInteger.ZERO;
        int problemStartCol = -1;

        for (int col = 0; col < maxWidth; col++) {
            boolean isEmptyCol = isColumnEmpty(rawLines, col);

            if (!isEmptyCol) {
                if (problemStartCol == -1) {
                    problemStartCol = col;
                }
            } else {

                if (problemStartCol != -1) {
                    grandTotal = grandTotal.add(solveHorizontallyBlock(rawLines, problemStartCol, col));
                    problemStartCol = -1;
                }
            }
        }

        if (problemStartCol != -1) {
            grandTotal = grandTotal.add(solveHorizontallyBlock(rawLines, problemStartCol, maxWidth));
        }

        System.out.println("Result Part 1: " + grandTotal);
    }

    private static void solvePart2(List<String> rawLines) {
        int maxWidth = 0;
        for (String line : rawLines) {
            maxWidth = Math.max(maxWidth, line.length());
        }
        BigInteger grandTotal = BigInteger.ZERO;
        int problemStartCol = -1;

        for (int col = 0; col < maxWidth; col++) {
            boolean isEmptyCol = isColumnEmpty(rawLines, col);

            if (!isEmptyCol) {
                if (problemStartCol == -1) {
                    problemStartCol = col;
                }
            } else {
                if (problemStartCol != -1) {
                    grandTotal = grandTotal.add(solveVerticalBlock(rawLines, problemStartCol, col));
                    problemStartCol = -1;
                }
            }
        }

        if (problemStartCol != -1) {
            grandTotal = grandTotal.add(solveVerticalBlock(rawLines, problemStartCol, maxWidth));
        }

        System.out.println("Result Part 2: " + grandTotal);
    }

    private static BigInteger solveHorizontallyBlock(List<String> lines, int startCol, int endCol) {
        List<BigInteger> numbers = new ArrayList<>();
        char operator = '?';

        for (String line : lines) {

            if (startCol >= line.length()) continue;
            int actualEnd = Math.min(endCol, line.length());
            String segment = line.substring(startCol, actualEnd).trim();

            if (segment.isEmpty()) continue;

            if (segment.equals("+") || segment.equals("*")) {
                operator = segment.charAt(0);
            } else {
                try {
                    numbers.add(new BigInteger(segment));
                } catch (NumberFormatException e) {
                }
            }
        }

        if (operator == '+') {
            BigInteger sum = BigInteger.ZERO;
            for (BigInteger num : numbers) {
                sum = sum.add(num);
            }
            return sum;
        } else if (operator == '*') {
            BigInteger prod = BigInteger.ONE;
            for (BigInteger num : numbers) {
                prod = prod.multiply(num);
            }
            return prod;
        }

        return BigInteger.ZERO;
    }

    private static BigInteger solveVerticalBlock(List<String> lines, int startCol, int endCol) {
        List<BigInteger> numbers = new ArrayList<>();
        char operator = '?';

        for (int col = startCol; col < endCol; col++) {
            StringBuilder sb = new StringBuilder();
            for (String line : lines) {
                if (col < line.length()) {
                    char c = line.charAt(col);

                    if (Character.isDigit(c)) {
                        sb.append(c);
                    } else if (c == '+' || c == '*') {
                        operator = c;
                    }
                }
            }

            if (sb.length() > 0) {
                try {
                    numbers.add(new BigInteger(sb.toString()));
                } catch (NumberFormatException e) {
                }
            }
        }
        if (operator == '+') {
            BigInteger sum = BigInteger.ZERO;
            for (BigInteger num : numbers) {
                sum = sum.add(num);
            }
            return sum;
        } else if (operator == '*') {
            BigInteger prod = BigInteger.ONE;
            for (BigInteger num : numbers) {
                prod = prod.multiply(num);
            }
            return prod;
        }

        return BigInteger.ZERO;
    }

    private static boolean isColumnEmpty(List<String> lines, int col) {
        for (String line : lines) {
            if (col < line.length() && line.charAt(col) != ' ') {
                return false;
            }
        }
        return true;
    }

    private static List<String> readRawInput(String filename) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("File read error: " + e.getMessage());
        }
        return lines;
    }
}