package com.cafsol.pricing.controller;

import com.cafsol.pricing.service.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/price")
public class PricingController {

    @Autowired
    private PricingService service;

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws Exception {
        service.loadTSV(file);
        return "TSV uploaded successfully";
    }

    @GetMapping
    public String getPrice(
            @RequestParam String skuid,
            @RequestParam(required = false) String time) {

        return service.getPrice(skuid, time);
    }
}
