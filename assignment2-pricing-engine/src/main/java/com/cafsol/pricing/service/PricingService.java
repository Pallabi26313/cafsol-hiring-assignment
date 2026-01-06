package com.cafsol.pricing.service;

import com.cafsol.pricing.model.Offer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.util.*;

@Service
public class PricingService {

    private final Map<String, List<Offer>> store = new HashMap<>();

    public void loadTSV(MultipartFile file) throws Exception {

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(file.getInputStream()));

        reader.lines()
              .skip(1) // skip header
              .forEach(line -> {
                  String[] p = line.split("\\|");

                  String sku = p[0].trim();
                  LocalTime start = LocalTime.parse(p[1].trim());
                  LocalTime end = LocalTime.parse(p[2].trim());
                  int price = Integer.parseInt(p[3].trim());

                  store.computeIfAbsent(sku, k -> new ArrayList<>())
                       .add(new Offer(start, end, price));
              });
    }

    public String getPrice(String skuid, String time) {

        if (!store.containsKey(skuid) || time == null) {
            return "NOT SET";
        }

        LocalTime t = LocalTime.parse(time);

        return store.get(skuid).stream()
                .filter(o -> !t.isBefore(o.getStart())
                          && t.isBefore(o.getEnd()))
                .map(o -> String.valueOf(o.getPrice()))
                .findFirst()
                .orElse("NOT SET");
    }
}
