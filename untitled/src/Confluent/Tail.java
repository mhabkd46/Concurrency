package Confluent;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Tail {
    
    public String getLastNLines(int n) {
        StringBuilder result = new StringBuilder();
        try(RandomAccessFile file = new RandomAccessFile("untitled/src/Confluent/MockFile.txt", "rw")) {
            long currentPointer = file.length() - 1;

            while (currentPointer >= 0) {
                file.seek(currentPointer);
                char c = (char) file.read();

                if (System.lineSeparator().equals(c)) {
                    n--;
                    if (n == 0) break;
                }

                result.append(c);
                currentPointer--;
            }
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }

        return result.reverse().toString();
    }
}
