package com.danielfreitassc.backend.services.user;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.danielfreitassc.backend.dtos.user.AuthenticationDTO;
import com.danielfreitassc.backend.dtos.user.LoginResponseDTO;
import com.danielfreitassc.backend.infra.security.TokenService;
import com.danielfreitassc.backend.models.user.UserEntity;
import com.danielfreitassc.backend.repositories.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserRepository userRepository;

    public LoginResponseDTO login(AuthenticationDTO authenticationDTO) {
        UserDetails userDetails = userRepository.findByUsername(authenticationDTO.username());
        if (!(userDetails instanceof UserEntity)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Usuário não encontrado.");
        }

        UserEntity user = (UserEntity) userDetails;
        if(user.isAccountLocked()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"A conta está bloqueada. Por favor, tente novamente mais tarde.");
        }

        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password());
            authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken(user);
            user.resetLoginAttempts();
            userRepository.save(user);

            return new LoginResponseDTO(token);
        } catch (Exception e) {
            user.incrementLoginAttempts();
            if(user.getLoginAttempts() >= 4) {
                user.lockAccount();
            }
            userRepository.save(user);
            int remainingAttempts = 4 - user.getLoginAttempts();

            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Senha incorreta: " + remainingAttempts + " tentativas restantes.");
        }
    }
}   
