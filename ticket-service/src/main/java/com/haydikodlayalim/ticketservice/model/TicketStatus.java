package com.haydikodlayalim.ticketservice.model;

import lombok.Getter;

@Getter
public enum TicketStatus {
    OPEN("Açık"),
    IN_PROGRESS("Devam Ediyor"),
    RESOLVED("Çözüldü"),
    CLOSED("Kapandı");

    private final String label;

    TicketStatus(String label) {
        this.label = label;
    }
}
