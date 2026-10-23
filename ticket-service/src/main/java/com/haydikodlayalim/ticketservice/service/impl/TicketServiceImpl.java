package com.haydikodlayalim.ticketservice.service.impl;

import com.haydikodlayalim.ticketservice.client.AccountServiceClient;
import com.haydikodlayalim.ticketservice.client.dto.AccountDto;
import com.haydikodlayalim.ticketservice.dto.TicketDto;
import com.haydikodlayalim.ticketservice.messaging.TicketNotificationService;
import com.haydikodlayalim.ticketservice.model.PriorityType;
import com.haydikodlayalim.ticketservice.model.Ticket;
import com.haydikodlayalim.ticketservice.model.TicketStatus;
import com.haydikodlayalim.ticketservice.model.elastic.TicketModel;
import com.haydikodlayalim.ticketservice.repository.elastic.TicketElasticRepository;
import com.haydikodlayalim.ticketservice.repository.mysql.TicketRepository;
import com.haydikodlayalim.ticketservice.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final TicketElasticRepository ticketElasticRepository;
    private final AccountServiceClient accountServiceClient;
    private final ModelMapper modelMapper;
    private final TicketNotificationService ticketNotificationService;

    @Override
    @Transactional
    public TicketDto save(TicketDto ticketDto) {
        if (ticketDto.getDescription() == null || ticketDto.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }

        Ticket ticket = new Ticket();
        ticket.setDescription(ticketDto.getDescription());
        ticket.setNotes(ticketDto.getNotes());
        ticket.setAssignee(ticketDto.getAssignee());
        ticket.setTicketDate(ticketDto.getTicketDate() != null ? ticketDto.getTicketDate() : new Date());
        ticket.setPriorityType(ticketDto.getPriorityType() != null ? ticketDto.getPriorityType() : PriorityType.LOW);
        ticket.setTicketStatus(ticketDto.getTicketStatus() != null ? ticketDto.getTicketStatus() : TicketStatus.OPEN);

        // 1. Save to MySQL
        ticket = ticketRepository.save(ticket);

        // 2. Save to Elasticsearch
        TicketModel ticketModel = TicketModel.builder()
                .id(ticket.getId())
                .description(ticket.getDescription())
                .notes(ticket.getNotes())
                .assignee(ticket.getAssignee())
                .ticketDate(ticket.getTicketDate())
                .priorityType(ticket.getPriorityType() != null ? ticket.getPriorityType().name() : null)
                .ticketStatus(ticket.getTicketStatus() != null ? ticket.getTicketStatus().name() : null)
                .build();
        ticketElasticRepository.save(ticketModel);

        ticketDto.setId(ticket.getId());
        ticketDto.setTicketDate(ticket.getTicketDate());
        ticketDto.setPriorityType(ticket.getPriorityType());
        ticketDto.setTicketStatus(ticket.getTicketStatus());

        enrichAssigneeDetails(ticketDto, ticket.getAssignee());

        ticketNotificationService.sendTicketCreatedNotification(ticket.getId(), ticket.getAssignee(), ticket.getDescription());

        return ticketDto;
    }

    @Override
    @Transactional
    public TicketDto update(String id, TicketDto ticketDto) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found: " + id));

        if (ticketDto.getDescription() != null) {
            ticket.setDescription(ticketDto.getDescription());
        }
        if (ticketDto.getNotes() != null) {
            ticket.setNotes(ticketDto.getNotes());
        }
        if (ticketDto.getAssignee() != null) {
            ticket.setAssignee(ticketDto.getAssignee());
        }
        if (ticketDto.getPriorityType() != null) {
            ticket.setPriorityType(ticketDto.getPriorityType());
        }
        if (ticketDto.getTicketStatus() != null) {
            ticket.setTicketStatus(ticketDto.getTicketStatus());
        }
        if (ticketDto.getTicketDate() != null) {
            ticket.setTicketDate(ticketDto.getTicketDate());
        }

        ticket = ticketRepository.save(ticket);

        TicketModel ticketModel = TicketModel.builder()
                .id(ticket.getId())
                .description(ticket.getDescription())
                .notes(ticket.getNotes())
                .assignee(ticket.getAssignee())
                .ticketDate(ticket.getTicketDate())
                .priorityType(ticket.getPriorityType() != null ? ticket.getPriorityType().name() : null)
                .ticketStatus(ticket.getTicketStatus() != null ? ticket.getTicketStatus().name() : null)
                .build();
        ticketElasticRepository.save(ticketModel);

        TicketDto responseDto = modelMapper.map(ticket, TicketDto.class);
        enrichAssigneeDetails(responseDto, ticket.getAssignee());
        return responseDto;
    }

    @Override
    public TicketDto getById(String id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found: " + id));
        TicketDto ticketDto = modelMapper.map(ticket, TicketDto.class);
        enrichAssigneeDetails(ticketDto, ticket.getAssignee());
        return ticketDto;
    }

    @Override
    public Page<TicketDto> getPagination(Pageable pageable) {
        return ticketElasticRepository.findAll(pageable)
                .map(model -> TicketDto.builder()
                        .id(model.getId())
                        .description(model.getDescription())
                        .notes(model.getNotes())
                        .assignee(model.getAssignee())
                        .ticketDate(model.getTicketDate())
                        .priorityType(model.getPriorityType() != null ? PriorityType.valueOf(model.getPriorityType()) : null)
                        .ticketStatus(model.getTicketStatus() != null ? TicketStatus.valueOf(model.getTicketStatus()) : null)
                        .build());
    }

    @Override
    @Transactional
    public void delete(String id) {
        ticketRepository.deleteById(id);
        ticketElasticRepository.deleteById(id);
    }

    private void enrichAssigneeDetails(TicketDto ticketDto, String assigneeId) {
        if (assigneeId == null || assigneeId.trim().isEmpty()) {
            return;
        }
        try {
            ResponseEntity<AccountDto> accountResponse = accountServiceClient.get(assigneeId);
            if (accountResponse != null && accountResponse.getBody() != null) {
                ticketDto.setAssigneeDetails(accountResponse.getBody());
            }
        } catch (Exception ex) {
            log.warn("Account service is currently unavailable or account not found for id: {}", assigneeId);
        }
    }
}
