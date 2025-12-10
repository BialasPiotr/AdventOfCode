import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day10 {

    public static void main(String[] args) {
        String filename = "Resources/input1.txt";
        String content = readWholeFile(filename);

        if (content.isEmpty()) {
            System.out.println("No solution found (empty file)");
        } else {
            solvePart1(content);
        }
    }

    private static void solvePart1(String content) {
        Pattern machinePattern = Pattern.compile("\\[([.#]+)\\](.*?)\\{[\\d,]+\\}", Pattern.DOTALL);
        Matcher m = machinePattern.matcher(content);

        long totalPresses = 0;
        int machinesFound = 0;

        while (m.find()) {
            machinesFound++;
            String diagram = m.group(1);
            String buttonsPart = m.group(2);
            totalPresses += solveMachine(diagram, buttonsPart);
        }

        System.out.println("Machines processed: " + machinesFound);
        System.out.println("Result Part 1: " + totalPresses);
    }

    private static int solveMachine(String diagram, String buttonsStr) {
        int targetMask = 0;
        for (int i = 0; i < diagram.length(); i++) {
            if (diagram.charAt(i) == '#') {
                targetMask |= (1 << i);
            }
        }

        List<Integer> buttons = new ArrayList<>();
        Pattern btnPattern = Pattern.compile("\\(([\\d,]+)\\)");
        Matcher bm = btnPattern.matcher(buttonsStr);

        while (bm.find()) {
            String nums = bm.group(1);
            int btnMask = 0;
            for (String s : nums.split(",")) {
                if (!s.trim().isEmpty()) {
                    int idx = Integer.parseInt(s.trim());
                    btnMask |= (1 << idx);
                }
            }
            buttons.add(btnMask);
        }

        int n = buttons.size();
        int minPresses = Integer.MAX_VALUE;
        int combinations = 1 << n;

        for (int i = 0; i < combinations; i++) {
            int currentMask = 0;
            int presses = 0;

            for (int b = 0; b < n; b++) {
                if ((i & (1 << b)) != 0) {
                    currentMask ^= buttons.get(b);
                    presses++;
                }
            }

            if (currentMask == targetMask) {
                if (presses < minPresses) {
                    minPresses = presses;
                }
            }
        }

        return (minPresses == Integer.MAX_VALUE) ? 0 : minPresses;
    }

    private static String readWholeFile(String filename) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append(" ");
            }
        } catch (IOException e) {
            System.err.println("File read error: " + e.getMessage());
        }
        return sb.toString();
    }
}