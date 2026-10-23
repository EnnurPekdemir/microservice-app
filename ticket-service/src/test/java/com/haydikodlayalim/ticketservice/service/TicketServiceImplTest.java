package com.haydikodlayalim.ticketservice.service;

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
import com.haydikodlayalim.ticketservice.service.impl.TicketServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketServiceImplTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private TicketElasticRepository ticketElasticRepository;

    @Mock
    private AccountServiceClient accountServiceClient;

    @Mock
    private TicketNotificationService ticketNotificationService;

    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    private TicketServiceImpl ticketService;

    private Ticket sampleTicket;
    private TicketDto sampleTicketDto;

    @BeforeEach
    void setUp() {
        sampleTicket = Ticket.builder()
                .id("ticket-123")
                .description("Sample ticket description")
                .notes("Sample notes")
                .assignee("account-1")
                .ticketDate(new Date())
                .priorityType(PriorityType.HIGH)
                .ticketStatus(TicketStatus.OPEN)
                .build();

        sampleTicketDto = TicketDto.builder()
                .id("ticket-123")
                .description("Sample ticket description")
                .notes("Sample notes")
                .assignee("account-1")
                .priorityType(PriorityType.HIGH)
                .ticketStatus(TicketStatus.OPEN)
                .build();
    }

    @Test
    void testSaveTicket() {
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> {
            Ticket t = invocation.getArgument(0);
            t.setId("ticket-123");
            return t;
        });
        when(ticketElasticRepository.save(any(TicketModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AccountDto mockAccount = AccountDto.builder().id("account-1").username("testuser").build();
        when(accountServiceClient.get("account-1")).thenReturn(ResponseEntity.ok(mockAccount));

        TicketDto result = ticketService.save(sampleTicketDto);

        assertNotNull(result);
        assertEquals("ticket-123", result.getId());
        assertEquals("Sample ticket description", result.getDescription());
        assertNotNull(result.getAssigneeDetails());
        assertEquals("testuser", result.getAssigneeDetails().getUsername());

        verify(ticketRepository).save(any(Ticket.class));
        verify(ticketElasticRepository).save(any(TicketModel.class));
        verify(ticketNotificationService).sendTicketCreatedNotification("ticket-123", "account-1", "Sample ticket description");
    }

    @Test
    void testSaveTicketWithoutDescriptionThrowsException() {
        TicketDto invalidDto = TicketDto.builder().description("").build();
        assertThrows(IllegalArgumentException.class, () -> ticketService.save(invalidDto));
    }

    @Test
    void testGetById() {
        when(ticketRepository.findById("ticket-123")).thenReturn(Optional.of(sampleTicket));

        TicketDto result = ticketService.getById("ticket-123");

        assertNotNull(result);
        assertEquals("ticket-123", result.getId());
        assertEquals(sampleTicket.getDescription(), result.getDescription());
    }

    @Test
    void testGetPagination() {
        TicketModel elasticModel = TicketModel.builder()
                .id("ticket-123")
                .description("Sample ticket description")
                .notes("Sample notes")
                .assignee("account-1")
                .ticketDate(new Date())
                .priorityType("HIGH")
                .ticketStatus("OPEN")
                .build();

        Pageable pageable = PageRequest.of(0, 10);
        when(ticketElasticRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(Collections.singletonList(elasticModel)));

        Page<TicketDto> pageResult = ticketService.getPagination(pageable);

        assertNotNull(pageResult);
        assertEquals(1, pageResult.getTotalElements());
        assertEquals("ticket-123", pageResult.getContent().get(0).getId());
    }

    @Test
    void testDelete() {
        ticketService.delete("ticket-123");
        verify(ticketRepository).deleteById("ticket-123");
        verify(ticketElasticRepository).deleteById("ticket-123");
    }
}
