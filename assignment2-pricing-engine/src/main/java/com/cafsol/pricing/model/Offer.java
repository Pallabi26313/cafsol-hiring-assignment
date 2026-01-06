package com.cafsol.pricing.model;

import java.time.LocalTime;

public class Offer {

    private LocalTime start;
    private LocalTime end;
    private int price;

    public Offer(LocalTime start, LocalTime end, int price) {
        this.start = start;
        this.end = end;
        this.price = price;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public int getPrice() {
        return price;
    }
}
