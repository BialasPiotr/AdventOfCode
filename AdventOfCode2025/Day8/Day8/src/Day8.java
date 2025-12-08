import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day8 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Advent of Code 2025: Day 8 ---");
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

        List<String> rawLines = readInput(filename);

        if (rawLines.isEmpty()) {
            System.out.println("No solution found (empty file)");
        } else {
            if (choice.equals("1")) {
                solvePart1(rawLines);
            } else {
                solvePart2(rawLines);
            }
        }
        scanner.close();
    }

    static class Point {
        int id;
        int x, y, z;

        public Point(int id, int x, int y, int z) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    static class Edge {
        int u, v;
        double distance;

        public Edge(int u, int v, double distance) {
            this.u = u;
            this.v = v;
            this.distance = distance;
        }
    }

    private static void solvePart1(List<String> rawLines) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < rawLines.size(); i++) {
            String[] coords = rawLines.get(i).split(",");
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);
            int z = Integer.parseInt(coords[2]);
            points.add(new Point(i, x, y, z));
        }

        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                Point p1 = points.get(i);
                Point p2 = points.get(j);
                double dist = Math.sqrt(Math.pow(p1.x - p2.x, 2) +
                        Math.pow(p1.y - p2.y, 2) +
                        Math.pow(p1.z - p2.z, 2));
                edges.add(new Edge(p1.id, p2.id, dist));
            }
        }

        edges.sort(Comparator.comparingDouble(e -> e.distance));

        int connectionsLimit = 1000;
        int n = points.size();

        int[] parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;

        int limit = Math.min(connectionsLimit, edges.size());
        for (int i = 0; i < limit; i++) {
            Edge edge = edges.get(i);
            union(parent, edge.u, edge.v);
        }

        Map<Integer, Integer> componentSizes = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int root = find(parent, i);
            componentSizes.put(root, componentSizes.getOrDefault(root, 0) + 1);
        }

        List<Integer> sizes = new ArrayList<>(componentSizes.values());
        Collections.sort(sizes, Collections.reverseOrder());

        long result = 1;
        for (int i = 0; i < Math.min(3, sizes.size()); i++) {
            result *= sizes.get(i);
        }

        System.out.println("Result Part 1: " + result);
    }

    private static void solvePart2(List<String> rawLines) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < rawLines.size(); i++) {
            String[] coords = rawLines.get(i).split(",");
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);
            int z = Integer.parseInt(coords[2]);
            points.add(new Point(i, x, y, z));
        }

        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                Point p1 = points.get(i);
                Point p2 = points.get(j);
                double dist = Math.sqrt(Math.pow(p1.x - p2.x, 2) +
                        Math.pow(p1.y - p2.y, 2) +
                        Math.pow(p1.z - p2.z, 2));
                edges.add(new Edge(p1.id, p2.id, dist));
            }
        }

        edges.sort(Comparator.comparingDouble(e -> e.distance));

        int n = points.size();
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;

        int componentsCount = n;

        for (Edge edge : edges) {
            int rootU = find(parent, edge.u);
            int rootV = find(parent, edge.v);

            if (rootU != rootV) {
                parent[rootU] = rootV;
                componentsCount--;

                if (componentsCount == 1) {
                    Point p1 = points.get(edge.u);
                    Point p2 = points.get(edge.v);
                    long result = (long) p1.x * (long) p2.x;
                    System.out.println("Result Part 2: " + result);
                    return;
                }
            }
        }
    }

    private static int find(int[] parent, int i) {
        if (parent[i] == i) {
            return i;
        }
        parent[i] = find(parent, parent[i]);
        return parent[i];
    }

    private static void union(int[] parent, int i, int j) {
        int rootI = find(parent, i);
        int rootJ = find(parent, j);
        if (rootI != rootJ) {
            parent[rootI] = rootJ;
        }
    }

    private static List<String> readInput(String filename) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line.trim());
                }
            }
        } catch (IOException e) {
            System.err.println("File read error: " + e.getMessage());
        }
        return lines;
    }
}