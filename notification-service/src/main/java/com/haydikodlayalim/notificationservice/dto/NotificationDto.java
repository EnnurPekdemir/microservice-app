package com.haydikodlayalim.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto implements Serializable {

    private String notificationId;
    private String accountId;
    private String ticketId;
    private String message;
    private Date timestamp;
}
