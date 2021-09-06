package com.eavy.account;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/token")
    @ResponseBody
    public String token() {
        return "ok";
    }

    @PostMapping("/signin")
    public ResponseEntity<AccountDTO> signIn(Account account) {
        AccountDTO accountDTO = accountService.signIn(account);
        if(accountDTO == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
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
