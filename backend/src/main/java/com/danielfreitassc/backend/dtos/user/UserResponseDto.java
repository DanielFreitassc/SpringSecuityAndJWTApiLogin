package com.danielfreitassc.backend.dtos.user;

import java.time.LocalDate;

import com.danielfreitassc.backend.models.user.UserLanguage;
import com.fasterxml.jackson.annotation.JsonFormat;

public record  UserResponseDto(
    Long id,
    String fullName,
    String username,
    String image,
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate birthDate,
    UserLanguage language
) {
    
}
