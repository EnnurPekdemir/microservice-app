package com.haydikodlayalim.notificationservice.service;

import com.haydikodlayalim.notificationservice.dto.NotificationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationDistributionService {

    public void sendNotification(NotificationDto notificationDto) {
        log.info("==================================================================");
        log.info("Notification received via RabbitMQ!");
        log.info("Notification ID : {}", notificationDto.getNotificationId());
        log.info("Ticket ID       : {}", notificationDto.getTicketId());
        log.info("Account ID      : {}", notificationDto.getAccountId());
        log.info("Message         : {}", notificationDto.getMessage());
        log.info("Timestamp       : {}", notificationDto.getTimestamp());
        log.info("==================================================================");
        // Here you can integrate with Email/SMS/Push notification providers (e.g. Twilio, SendGrid, Firebase)
    }
}
