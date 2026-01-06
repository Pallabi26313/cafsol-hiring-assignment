package com.cafsol.configparser.parser;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Component
public class ConfigFileParser {

    public Map<String, Map<String, Object>> parse(Path path) throws IOException {
        Map<String, Map<String, Object>> store = new HashMap<>();
        String currentSection = null;

        for (String line : Files.readAllLines(path)) {
            line = line.trim();
            if (line.isEmpty()) continue;

            if (!line.contains("=")) {
                currentSection = line;
                store.putIfAbsent(currentSection, new HashMap<>());
            } else {
                String[] parts = line.split("=", 2);
                String key = parts[0].trim();
                String value = parts[1].trim();

                if (value.contains(",")) {
                    store.get(currentSection)
                         .put(key, Arrays.stream(value.split(","))
                         .map(String::trim)
                         .toList());
                } else {
                    store.get(currentSection).put(key, value);
                }
            }
        }
        return store;
    }
}
