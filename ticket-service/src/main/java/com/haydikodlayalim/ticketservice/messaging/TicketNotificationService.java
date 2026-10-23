package com.haydikodlayalim.ticketservice.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketNotificationService {

    private final StreamBridge streamBridge;

    public void sendTicketCreatedNotification(String ticketId, String accountId, String description) {
        try {
            TicketNotification notification = TicketNotification.builder()
                    .notificationId(UUID.randomUUID().toString())
                    .ticketId(ticketId)
                    .accountId(accountId)
                    .message("Ticket created: " + description)
                    .timestamp(new Date())
                    .build();

            // Sends to 'ticketNotification-out-0' binding (destination: notification-topic)
            boolean sent = streamBridge.send("ticketNotification-out-0", notification);
            if (sent) {
                log.info("Ticket notification published to RabbitMQ successfully for ticket: {}", ticketId);
            } else {
                log.warn("Failed to publish ticket notification to RabbitMQ for ticket: {}", ticketId);
            }
        } catch (Exception ex) {
            log.warn("Could not dispatch notification event to RabbitMQ: {}", ex.getMessage());
        }
    }
}
