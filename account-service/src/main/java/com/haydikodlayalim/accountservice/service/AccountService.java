package com.haydikodlayalim.accountservice.service;

import com.haydikodlayalim.accountservice.dto.AccountDto;
import com.haydikodlayalim.accountservice.entity.Account;
import com.haydikodlayalim.accountservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    public AccountDto get(String id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + id));
        return modelMapper.map(account, AccountDto.class);
    }

    public AccountDto save(AccountDto accountDto) {
        Account account = modelMapper.map(accountDto, Account.class);
        account = accountRepository.save(account);
        return modelMapper.map(account, AccountDto.class);
    }

    public AccountDto update(String id, AccountDto accountDto) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + id));
        account.setUsername(accountDto.getUsername());
        account.setEmail(accountDto.getEmail());
        account = accountRepository.save(account);
        return modelMapper.map(account, AccountDto.class);
    }

    public void delete(String id) {
        accountRepository.deleteById(id);
    }

    public List<AccountDto> pagination() {
        return StreamSupport.stream(accountRepository.findAll().spliterator(), false)
                .map(item -> modelMapper.map(item, AccountDto.class))
                .collect(Collectors.toList());
    }
}
