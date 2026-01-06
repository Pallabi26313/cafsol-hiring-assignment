package com.cafsol.configparser.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafsol.configparser.parser.ConfigFileParser;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

@Service
public class ConfigService {

    private Map<String, Map<String, Object>> configStore;

    @Autowired
    public ConfigService(ConfigFileParser parser) throws IOException {
        this.configStore = parser.parse(
            Paths.get("src/main/resources/config.txt")
        );
    }

    public Map<String, Object> getSection(String section) {
        return configStore.get(section);
    }
}
