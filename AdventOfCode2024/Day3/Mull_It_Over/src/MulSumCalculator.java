import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MulSumCalculator {
    public static void main(String[] args) {
        String filePath = "input.txt";

        try {

            String content = new String(Files.readAllBytes(Paths.get(filePath)));

            Pattern pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
            Matcher matcher = pattern.matcher(content);

            long sum = 0;

            while (matcher.find()) {

                int x = Integer.parseInt(matcher.group(1));
                int y = Integer.parseInt(matcher.group(2));

                sum += (long) x * y;
            }

            System.out.println("Suma wyników mnożeń: " + sum);

        } catch (IOException e) {
            
            System.err.println("Błąd podczas odczytu pliku: " + e.getMessage());
        }
    }
}
