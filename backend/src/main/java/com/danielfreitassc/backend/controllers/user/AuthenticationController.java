package com.danielfreitassc.backend.controllers.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danielfreitassc.backend.dtos.user.AuthenticationDTO;
import com.danielfreitassc.backend.dtos.user.LoginResponseDTO;
import com.danielfreitassc.backend.services.user.AuthenticationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody @Valid AuthenticationDTO authenticationDTO) {
        return  authenticationService.login(authenticationDTO);
    }
}
