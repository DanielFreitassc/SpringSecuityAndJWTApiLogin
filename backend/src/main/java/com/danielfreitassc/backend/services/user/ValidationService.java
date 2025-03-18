package com.danielfreitassc.backend.services.user;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.danielfreitassc.backend.dtos.user.ValidationResponseDTO;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidationService {
    
    public ValidationResponseDTO validate(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.isAuthenticated()) {
            if(request.isUserInRole("ADMIN")) {
                return new ValidationResponseDTO("Autorizado","ADMIN");
            } else if(request.isUserInRole("USER")) {
                return new ValidationResponseDTO("Autorizado","USER");
            }
        }
        
        throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Não autorizado, você não possui um cargo!");
    }   
}
