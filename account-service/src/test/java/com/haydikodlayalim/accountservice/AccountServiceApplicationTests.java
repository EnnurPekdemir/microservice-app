package com.haydikodlayalim.accountservice;

import com.haydikodlayalim.accountservice.dto.AccountDto;
import com.haydikodlayalim.accountservice.entity.Account;
import com.haydikodlayalim.accountservice.repository.AccountRepository;
import com.haydikodlayalim.accountservice.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceApplicationTests {

    @Mock
    private AccountRepository accountRepository;

    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    private AccountService accountService;

    private Account sampleAccount;

    @BeforeEach
    void setUp() {
        sampleAccount = new Account();
        sampleAccount.setId("acc-1");
        sampleAccount.setUsername("johndoe");
        sampleAccount.setEmail("john@example.com");
    }

    @Test
    void testGetAccount() {
        when(accountRepository.findById("acc-1")).thenReturn(Optional.of(sampleAccount));

        AccountDto result = accountService.get("acc-1");

        assertNotNull(result);
        assertEquals("acc-1", result.getId());
        assertEquals("johndoe", result.getUsername());
        verify(accountRepository).findById("acc-1");
    }

    @Test
    void testSaveAccount() {
        when(accountRepository.save(any(Account.class))).thenReturn(sampleAccount);

        AccountDto inputDto = AccountDto.builder().username("johndoe").email("john@example.com").build();
        AccountDto result = accountService.save(inputDto);

        assertNotNull(result);
        assertEquals("johndoe", result.getUsername());
        verify(accountRepository).save(any(Account.class));
    }
}
