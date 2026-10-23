package com.haydikodlayalim.accountservice.api;

import com.haydikodlayalim.accountservice.dto.AccountDto;
import com.haydikodlayalim.accountservice.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountApi {

    private final AccountService accountService;

    public AccountApi(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> get(@PathVariable("id") String id) {
        return ResponseEntity.ok(accountService.get(id));
    }

    @PostMapping
    public ResponseEntity<AccountDto> save(@RequestBody AccountDto account) {
        return ResponseEntity.ok(accountService.save(account));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDto> update(@PathVariable("id") String id, @RequestBody AccountDto account) {
        return ResponseEntity.ok(accountService.update(id, account));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        accountService.delete(id);
    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> pagination() {
        return ResponseEntity.ok(accountService.pagination());
    }
}
