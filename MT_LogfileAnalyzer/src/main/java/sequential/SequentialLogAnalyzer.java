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

        String[] files = new String[5];

        files[0] = "app-2025-03-30.log";
        files[1] = "app-2025-03-31.log";
        files[2] = "app-2025-04-01.log";
        files[3] = "app-2025-04-02.log";
        files[4] = "app-2025-04-03.log";

        String[] parts;

        // Startzeit messen
        long startTime = System.nanoTime();

        for (int i = 0; i < files.length; i++) {
            try (BufferedReader br = new BufferedReader(new FileReader(files[i]))) {
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

        // Endzeit messen
        long endTime = System.nanoTime();

        // Laufzeit berechnen und in Sekunden umwandeln
        long nanoSec = endTime - startTime;
        double sec = nanoSec / 1000000000.0;
        System.out.println("TRACE count: " + traceCount
                + "\nINFO count: " + infoCount
                + "\nWARN count: " + warnCount
                + "\nERROR count: " + errorCount
                + "\nDEBUG count: " + debugCount);

        System.out.println("Laufzeit: " + sec + " Sekunden oder " + nanoSec + " Nanosekunden");
    }
}
