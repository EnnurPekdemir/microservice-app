package com.haydikodlayalim.notificationservice.messaging;

import com.haydikodlayalim.notificationservice.dto.NotificationDto;
import com.haydikodlayalim.notificationservice.service.NotificationDistributionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.function.Consumer;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NotificationListenerTest {

    @Mock
    private NotificationDistributionService notificationDistributionService;

    @InjectMocks
    private NotificationListener notificationListener;

    @Test
    void testNotificationConsumerReceivesAndDispatchesMessage() {
        NotificationDto sampleDto = NotificationDto.builder()
                .notificationId("notif-1")
                .ticketId("ticket-100")
                .accountId("acc-200")
                .message("New ticket assigned")
                .timestamp(new Date())
                .build();

        Consumer<NotificationDto> consumer = notificationListener.notificationConsumer();
        consumer.accept(sampleDto);

        verify(notificationDistributionService).sendNotification(sampleDto);
    }
}
