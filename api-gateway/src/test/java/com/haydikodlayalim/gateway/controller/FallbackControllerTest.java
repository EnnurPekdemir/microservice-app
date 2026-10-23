package com.haydikodlayalim.gateway.controller;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FallbackControllerTest {

    private final FallbackController fallbackController = new FallbackController();

    @Test
    void testAccountFallback() {
        Map<String, Object> response = fallbackController.accountFallback().block();

        assertNotNull(response);
        assertEquals("SERVICE_UNAVAILABLE", response.get("status"));
        assertNotNull(response.get("message"));
    }

    @Test
    void testTicketFallback() {
        Map<String, Object> response = fallbackController.ticketFallback().block();

        assertNotNull(response);
        assertEquals("SERVICE_UNAVAILABLE", response.get("status"));
        assertNotNull(response.get("message"));
    }
}
