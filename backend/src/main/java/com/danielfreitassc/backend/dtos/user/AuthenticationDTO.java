package com.danielfreitassc.backend.dtos.user;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(
    @NotBlank(message="O campo de username não pode estar em branco") String username,
    @NotBlank(message="O campo de senha não pode estar em branco") String password
) {
    
}
