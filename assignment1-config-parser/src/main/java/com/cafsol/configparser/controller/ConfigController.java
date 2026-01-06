package com.cafsol.configparser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cafsol.configparser.service.ConfigService;

import java.util.Map;

@RestController
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private ConfigService service;

    @GetMapping
    public ResponseEntity<?> getConfig(@RequestParam String section) {
        Map<String, Object> data = service.getSection(section);
        if (data == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(data);
    }
}
