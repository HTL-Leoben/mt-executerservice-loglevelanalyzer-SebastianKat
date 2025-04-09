package parallel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelLogAnalyzer {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
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

        // Startzeit messen
        long startTime = System.nanoTime();

        // ExecutorService mit einer festen Anzahl von Threads erstellen
        int numberOfCores = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfCores);

        // Liste für Future-Objekte
        List<Future<Map<String, Integer>>> futures = new ArrayList<>();

        // LogAnalyzerTask-Instanzen erstellen und an den ExecutorService übergeben
        for (String file : files) {
            Callable<Map<String, Integer>> task = new LogAnalyzerTask(file);
            Future<Map<String, Integer>> future = executorService.submit(task);
            futures.add(future);
        }

        // Gesamtzählungen initialisieren
        int totalTraceCount = 0;
        int totalInfoCount = 0;
        int totalWarnCount = 0;
        int totalErrorCount = 0;
        int totalDebugCount = 0;

        // Ergebnisse aggregieren
        for (Future<Map<String, Integer>> future : futures) {
            Map<String, Integer> result = future.get();
            totalTraceCount += result.getOrDefault("TRACE", 0);
            totalInfoCount += result.getOrDefault("INFO", 0);
            totalWarnCount += result.getOrDefault("WARN", 0);
            totalErrorCount += result.getOrDefault("ERROR", 0);
            totalDebugCount += result.getOrDefault("DEBUG", 0);
        }

        // Endzeit messen
        long endTime = System.nanoTime();

        // Laufzeit berechnen und in Sekunden umwandeln
        long nanoSec = (endTime - startTime);
        long milliSec = nanoSec / 1_000_000;

        // Ausgabe formatieren
        System.out.printf("Log Analyse Ergebnisse:%n");
        System.out.printf("Gesamt TRACE count: %,d%n", totalTraceCount);
        System.out.printf("Gesamt INFO count:  %,d%n", totalInfoCount);
        System.out.printf("Gesamt WARN count:  %,d%n", totalWarnCount);
        System.out.printf("Gesamt ERROR count: %,d%n", totalErrorCount);
        System.out.printf("Gesamt DEBUG count: %,d%n", totalDebugCount);
        System.out.printf("Laufzeit: " + milliSec + " milliSec");

        // ExecutorService herunterfahren
        executorService.shutdown();
    }
}


