package com.danielfreitassc.backend.services.user;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.danielfreitassc.backend.models.user.UserEntity;
import com.danielfreitassc.backend.models.user.UserLanguage;
import com.danielfreitassc.backend.models.user.UserRole;
import com.danielfreitassc.backend.repositories.user.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminUserInitializer {
    private final UserRepository userRepository;
    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate birthDate = LocalDate.parse("07/02/2003", formatter);
    
    @PostConstruct
    public void init() {

        if(userRepository.findByUsername(adminUsername) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Usuário já existe");
        } else {
            String encryptedPassword =  new BCryptPasswordEncoder().encode(adminPassword);
            UserEntity userEntity = new UserEntity(adminUsername, adminUsername, "foto", UserLanguage.PORTUGUESE,birthDate,  encryptedPassword, UserRole.ADMIN);
            userRepository.save(userEntity);
        }
    }
}
