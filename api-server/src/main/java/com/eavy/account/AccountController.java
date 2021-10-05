package com.eavy.account;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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

    @PostMapping("/signin")
    public ResponseEntity<AccountDTO> signIn(Account account) {
        AccountDTO accountDTO = accountService.signIn(account);
        if(accountDTO == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(accountDTO);
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
        Optional<Account> optionalAccount = accountRepository.findByUserId(principal.getName());
        AccountDTO accountDTO = objectMapper.convertValue(optionalAccount.get(), AccountDTO.class);
        return ResponseEntity.ok(accountDTO);
    }

    // TODO Test
    @GetMapping("/token/check")
    public ResponseEntity checkToken() {
        return ResponseEntity.ok(null);
    }

    // TODO Test
    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes(StandardCharsets.UTF_8));
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                Optional<Account> optionalAccount = accountRepository.findByUserId(username);
                Account account = optionalAccount.orElse(null);
                String accessToken = JWT.create()
                        .withSubject(account.getUserId())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withClaim("roles", account.getRoles().stream().map(AccountRole::name).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access-token", accessToken);
                tokens.put("refresh-token", refreshToken);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error-message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }
        else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

}
