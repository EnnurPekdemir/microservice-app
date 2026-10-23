package com.haydikodlayalim.ticketservice.model;

import lombok.Getter;

@Getter
public enum PriorityType {
    URGENT("Acil"),
    LOW("Düşük"),
    HIGH("Yüksek");

    private final String label;

    PriorityType(String label) {
        this.label = label;
    }
}
