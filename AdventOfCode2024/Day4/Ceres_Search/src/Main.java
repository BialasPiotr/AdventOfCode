import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Please select an option: 1 - Run Part One, 2 - Run Part Two");

        Scanner inputScanner = new Scanner(System.in);
        String choice = inputScanner.nextLine();

        if (choice.equals("1")) {
            PartOne.run();
        } else if (choice.equals("2")) {
            PartTwo.run();
        } else {
            System.out.println("Invalid option. Please select 1 or 2.");
        }
    }
}