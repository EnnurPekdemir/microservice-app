package com.haydikodlayalim.notificationservice.messaging;

import com.haydikodlayalim.notificationservice.dto.NotificationDto;
import com.haydikodlayalim.notificationservice.service.NotificationDistributionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class NotificationListener {

    private final NotificationDistributionService notificationDistributionService;

    /**
     * Spring Cloud Stream Consumer functional bean.
     * Bound to RabbitMQ exchange/queue via 'notificationConsumer-in-0' binding in properties.
     */
    @Bean
    public Consumer<NotificationDto> notificationConsumer() {
        return notificationDto -> {
            log.info("Consumer received notification payload: {}", notificationDto);
            notificationDistributionService.sendNotification(notificationDto);
        };
    }
}
