package com.cafsol.pricing;

import com.cafsol.pricing.service.PricingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PricingServiceTest {

    private PricingService service;

    @BeforeEach
    void setup() throws Exception {

        service = new PricingService();

        String data = """
SkuID|StartTime|EndTime|Price
u00006541|10:00|10:15|101
u00006541|10:05|10:10|99
""";

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "data.tsv",
                "text/plain",
                data.getBytes()
        );

        service.loadTSV(file);
    }

    @Test
    void priceAt1003ShouldBe101() {
        assertEquals("101",
                service.getPrice("u00006541", "10:03"));
    }

    @Test
    void priceAt1005ShouldBe99() {
        assertEquals("99",
                service.getPrice("u00006541", "10:05"));
    }

    @Test
    void priceBeforeWindowShouldBeNotSet() {
        assertEquals("NOT SET",
                service.getPrice("u00006541", "09:55"));
    }
}
