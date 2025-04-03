import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class SequentialLogAnalyzer {

    public static void main(String[] args) {
        // Pfade zu den Logdateien
        String[] logFileNames = {"app-2025-03-30.log", "app-2025-03-31.log", "app-2025-04-01.log", "app-2025-04-02.log", "app-2025-04-03.log"};

        // Gesamtzählungen für alle Dateien
        Map<String, Integer> totalCounts = new HashMap<>();
        initializeLogLevelCounts(totalCounts);

        // Startzeit messen
        long startTime = System.nanoTime();

        // Jede Logdatei sequentiell analysieren
        for (String fileName : logFileNames) {
            Path filePath = Paths.get(fileName);
            if (Files.exists(filePath)) {
                Map<String, Integer> fileCounts = analyzeLogFile(filePath);
                System.out.println("Ergebnisse für " + fileName + ": " + fileCounts);

                // Aggregieren der Zählungen
                aggregateCounts(totalCounts, fileCounts);
            } else {
                System.out.println("Datei nicht gefunden: " + fileName);
            }
        }

        // Endzeit messen
        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        // Gesamtzusammenfassung ausgeben
        System.out.println("Gesamtzusammenfassung: " + totalCounts);
        System.out.println("Ausführungszeit: " + duration / 1_000_000 + " ms");
    }

    private static Map<String, Integer> analyzeLogFile(Path filePath) {
        Map<String, Integer> logLevelCounts = new HashMap<>();
        initializeLogLevelCounts(logLevelCounts);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("TRACE")) logLevelCounts.put("TRACE", logLevelCounts.get("TRACE") + 1);
                else if (line.contains("DEBUG")) logLevelCounts.put("DEBUG", logLevelCounts.get("DEBUG") + 1);
                else if (line.contains("INFO")) logLevelCounts.put("INFO", logLevelCounts.get("INFO") + 1);
                else if (line.contains("WARN")) logLevelCounts.put("WARN", logLevelCounts.get("WARN") + 1);
                else if (line.contains("ERROR")) logLevelCounts.put("ERROR", logLevelCounts.get("ERROR") + 1);
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Lesen der Datei: " + e.getMessage());
        }

        return logLevelCounts;
    }

    private static void initializeLogLevelCounts(Map<String, Integer> logLevelCounts) {
        logLevelCounts.put("TRACE", 0);
        logLevelCounts.put("DEBUG", 0);
        logLevelCounts.put("INFO", 0);
        logLevelCounts.put("WARN", 0);
        logLevelCounts.put("ERROR", 0);
    }

    private static void aggregateCounts(Map<String, Integer> totalCounts, Map<String, Integer> fileCounts) {
        for (String level : totalCounts.keySet()) {
            totalCounts.put(level, totalCounts.get(level) + fileCounts.get(level));
        }
    }
}
