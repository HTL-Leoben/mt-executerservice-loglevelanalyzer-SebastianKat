package sequential;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SequentialLogAnalyzer {

    public static void main(String[] args) throws IOException {
        int traceCount = 0;
        int infoCount = 0;
        int warnCount = 0;
        int errorCount = 0;
        int debugCount = 0;

        String[] files = {
                "app-2025-03-30.log",
                "app-2025-03-31.log",
                "app-2025-04-01.log",
                "app-2025-04-02.log",
                "app-2025-04-03.log",
                "app-2025-04-05.log",
                "app-2025-04-06.log",
                "app-2025-04-07.log",
                "app-2025-04-08.log",
                "app-2025-04-09.log"
        };

        String[] parts;

        long startTime = System.nanoTime();

        for (String file : files) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    parts = line.split(" ");
                    switch (parts[1]) {
                        case "TRACE":
                            traceCount++;
                            break;
                        case "INFO":
                            infoCount++;
                            break;
                        case "WARN":
                            warnCount++;
                            break;
                        case "ERROR":
                            errorCount++;
                            break;
                        case "DEBUG":
                            debugCount++;
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        long endTime = System.nanoTime();

        long nanoSec = (endTime - startTime);
        long milliSec = nanoSec / 1_000_000;

        System.out.printf("Log Analyse Ergebnisse:%n");
        System.out.printf("TRACE count: %,d%n", traceCount);
        System.out.printf("INFO count:  %,d%n", infoCount);
        System.out.printf("WARN count:  %,d%n", warnCount);
        System.out.printf("ERROR count: %,d%n", errorCount);
        System.out.printf("DEBUG count: %,d%n", debugCount);
        System.out.printf("Laufzeit: " + milliSec + " Millisekunden");
    }
}
