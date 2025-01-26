package com.stock.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stock.dto.TickerRequestDTO;
import com.stock.dto.TickerResponseDTO;
import com.stock.dto.UserAuthDTO;
import com.stock.dto.UserRegistryDTO;
import com.stock.service.CurrentUserService;
import com.stock.service.TickerHistoryService;
import com.stock.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final CurrentUserService currentUserService;
    private final TickerHistoryService tickerHistoryService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid UserRegistryDTO registryDTO) {
        userService.addUser(registryDTO);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid UserAuthDTO authDTO) {
        var token = userService.authenticate(authDTO);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/save")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> saveTickerListForUser(@RequestBody @Valid TickerRequestDTO tickerRequestDTO) throws JsonProcessingException {
        tickerHistoryService.addTickerHistory(currentUserService.getUser(), tickerRequestDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/saved")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getTicskerListFromUser(@RequestParam @Valid @NotBlank(message="Ticket is empty") String ticker) throws JsonProcessingException {
        TickerResponseDTO tickerHistory = tickerHistoryService.getTickerHistory(currentUserService.getUser(), ticker);
        return ResponseEntity.ok(tickerHistory);
    }
}
