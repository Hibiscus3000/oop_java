import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        try {
            String readFileName, writeFileName;
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            readFileName = reader.readLine();
            writeFileName = reader.readLine();
            WordCounter wordCounter = new WordCounter();
            wordCounter.readFile(readFileName);
            wordCounter.writeFile(writeFileName);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
