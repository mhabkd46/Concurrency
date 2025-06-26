package Confluent;

import java.io.IOException;
import java.io.RandomAccessFile;

/*
1. getLastNLines
2. Do it without storing it in StringBuilder. (cursor goes back n lines. cursor goes forward n lines to print)
3. How do you optimize file read? (file.read() gets single bytes from Disk. Batch the operation)
 */
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
