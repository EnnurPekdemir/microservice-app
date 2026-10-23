package com.haydikodlayalim.adminserver;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AdminServerApplicationTests {

    @Test
    void testAdminServerInitialization() {
        // Simple sanity test verifying application class is present
        assertTrue(AdminServerApplication.class.isAnnotationPresent(de.codecentric.boot.admin.server.config.EnableAdminServer.class));
    }
}
