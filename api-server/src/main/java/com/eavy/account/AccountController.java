package com.eavy.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class AccountController {

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final ObjectMapper objectMapper;

    public AccountController(AccountService accountService, AccountRepository accountRepository, ObjectMapper objectMapper) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.objectMapper = objectMapper;
    }

    // TODO Test
    @GetMapping("/users")
    public ResponseEntity getUser(@RequestParam String userId) {
        Optional<Account> optionalAccount = accountRepository.findByUserId(userId);
        if(optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            AccountDTO accountDTO = objectMapper.convertValue(account, AccountDTO.class);
            return ResponseEntity.ok(accountDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/signin")
    public ResponseEntity<AccountDTO> signIn(Account account) {
        AccountDTO accountDTO = accountService.signIn(account);
        if(accountDTO == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(accountDTO);
    }

    @PostMapping("/signup")
    public ResponseEntity<AccountDTO> signUp(Account account) {
        Account savedAccount = accountService.signUp(account);
        if(savedAccount == null)
            return ResponseEntity.badRequest().body(null);
        return ResponseEntity.ok().body(null);
    }

}
