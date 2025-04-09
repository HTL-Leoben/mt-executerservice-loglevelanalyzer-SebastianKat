package parallel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

class LogAnalyzerTask implements Callable<Map<String, Integer>> {
    private final String file;

    public LogAnalyzerTask(String file) {
        this.file = file;
    }

    @Override
    public Map<String, Integer> call() throws Exception {


        Map<String, Integer> logLevelCounts = new HashMap<>();
        logLevelCounts.put("TRACE", 0);
        logLevelCounts.put("INFO", 0);
        logLevelCounts.put("WARN", 0);
        logLevelCounts.put("ERROR", 0);
        logLevelCounts.put("DEBUG", 0);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length > 1) {
                    switch (parts[1]) {
                        case "TRACE":
                            logLevelCounts.put("TRACE", logLevelCounts.get("TRACE") + 1);
                            break;
                        case "INFO":
                            logLevelCounts.put("INFO", logLevelCounts.get("INFO") + 1);
                            break;
                        case "WARN":
                            logLevelCounts.put("WARN", logLevelCounts.get("WARN") + 1);
                            break;
                        case "ERROR":
                            logLevelCounts.put("ERROR", logLevelCounts.get("ERROR") + 1);
                            break;
                        case "DEBUG":
                            logLevelCounts.put("DEBUG", logLevelCounts.get("DEBUG") + 1);
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logLevelCounts;
    }
}