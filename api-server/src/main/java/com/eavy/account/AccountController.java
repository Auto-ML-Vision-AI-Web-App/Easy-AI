package com.eavy.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class AccountController {

    private final AccountService accountService;
    private final ObjectMapper objectMapper;

    public AccountController(AccountService accountService, ObjectMapper objectMapper) {
        this.accountService = accountService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/signin")
    public ResponseEntity<AccountDto> signIn(Account account) {
        AccountDto accountDto = accountService.signIn(account);
        if(accountDto == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(accountDto);
    }

    @PostMapping("/signup")
    public ResponseEntity signUp(@Valid Account account, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("username and password should be not empty");
        }
        try {
            Account savedAccount = accountService.signUp(account);
            return ResponseEntity.ok().body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/users")
    public ResponseEntity getUser(Principal principal) {
        Optional<Account> optionalAccount = accountService.findByUserId(principal.getName());
        AccountDto accountDTO = objectMapper.convertValue(optionalAccount.get(), AccountDto.class);
        return ResponseEntity.ok(accountDTO);
    }

}
