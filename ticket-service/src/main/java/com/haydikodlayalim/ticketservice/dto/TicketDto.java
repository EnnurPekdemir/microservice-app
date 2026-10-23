package com.haydikodlayalim.ticketservice.dto;

import com.haydikodlayalim.ticketservice.client.dto.AccountDto;
import com.haydikodlayalim.ticketservice.model.PriorityType;
import com.haydikodlayalim.ticketservice.model.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto implements Serializable {

    private String id;
    private String description;
    private String notes;
    private String assignee;
    private Date ticketDate;
    private PriorityType priorityType;
    private TicketStatus ticketStatus;
    private AccountDto assigneeDetails;
}
