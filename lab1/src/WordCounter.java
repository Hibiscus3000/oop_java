import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

public class WordCounter {
    private Map<String, Integer> unsortedMap = new HashMap<>();
    private int count = 0;

    private void readText(BufferedReader reader) throws IOException {
        String line = null, word = null;
        int start, i, lineLength;
        Integer frequency;
        try {
            while ((line = reader.readLine()) != null) {
                start = 0;
                lineLength = line.length();
                for (i = 0; i < lineLength; ++i) {
                    if (!Character.isLetterOrDigit(line.charAt(i))) {
                        if (i != start) {
                            ++count;
                            word = line.substring(start, i);
                            frequency = unsortedMap.get(word);
                            unsortedMap.put(word, (frequency == null) ? 1 : (frequency + 1));
                        }
                        start = i + 1;
                    }
                }
                if (Character.isLetterOrDigit(line.charAt(lineLength - 1))) {
                    ++count;
                    word = line.substring(start, lineLength);
                    frequency = unsortedMap.get(word);
                    unsortedMap.put(word, (frequency == null) ? 1 : (frequency + 1));
                }
            }
        } catch (IOException e) {
            throw e;
        }
    }

    public void readFile(String readFileName) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(readFileName));
            readText(reader);
        } catch (IOException e) {
            System.err.println("Error while reading file: " + e.getLocalizedMessage());
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        }

    }

    public void writeFile(String writeFileName) {
        List<Entry<String, Integer>> list = new ArrayList<>(unsortedMap.entrySet());
        list.sort(Entry.comparingByValue());
        Collections.reverse(list);
        FileWriter writer = null;
        try {
            writer = new FileWriter(writeFileName);
            for (Entry<String, Integer> word : list)
                writer.write(word.getKey() + ',' + word.getValue() + ',' + (double) (word.getValue() * 10000 / count) / 100 + "%\n");
        } catch (IOException e) {
            System.err.println("Error while reading file: " + e.getLocalizedMessage());
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        }
    }
}
