package com.cafsol.configparser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.cafsol.configparser.controller.ConfigController;
import com.cafsol.configparser.service.ConfigService;

@WebMvcTest(ConfigController.class)
class ConfigControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConfigService configService;

    @Test
    void shouldReturnOrderServiceConfig() throws Exception {

        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("broker", "https://orbroker.in");
        mockResponse.put(
            "topic",
            List.of("test_os_topic_1", "test_os_topic_2")
        );

        when(configService.getSection("Order Service"))
            .thenReturn(mockResponse);

        mockMvc.perform(get("/config")
                .param("section", "Order Service"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.broker")
                .value("https://orbroker.in"))
            .andExpect(jsonPath("$.topic").isArray());
    }
}
