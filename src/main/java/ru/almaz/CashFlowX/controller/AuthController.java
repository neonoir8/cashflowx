package ru.almaz.CashFlowX.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.almaz.CashFlowX.dto.AuthRequestDTO;
import ru.almaz.CashFlowX.dto.AuthResponseDTO;
import ru.almaz.CashFlowX.dto.UserDTO;
import ru.almaz.CashFlowX.security.jwt.JwtUtil;
import ru.almaz.CashFlowX.service.AuthService;
import ru.almaz.CashFlowX.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO request) {
        userService.createUser(request);
        return ResponseEntity.ok("Пользователь успешно зарегистрирован");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> createAuthToken(@RequestBody AuthRequestDTO authRequest) {
        AuthResponseDTO response = authService.authenticate(authRequest);
        return ResponseEntity.ok(response);
    }
}
