package com.danielfreitassc.backend.dtos.user;


import java.time.LocalDate;

import com.danielfreitassc.backend.configurations.OnCreate;
import com.danielfreitassc.backend.models.user.UserLanguage;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record  UserRequestDto(
    @NotBlank(groups=OnCreate.class, message="O campo nome não pode estar em branco.") 
    String fullName,

    @NotBlank(groups=OnCreate.class, message="O campo username não pode estar em branco.") 
    String username,

    @NotBlank(groups=OnCreate.class, message="Imagem não pode estar vazia.") 
    String image,

    @JsonFormat(pattern="dd/MM/yyyy")
    @NotNull(groups=OnCreate.class, message="O campo data de arniversario não pode ser nulo") 
    LocalDate birthDate,

    @NotNull(groups=OnCreate.class, message="O campo linguagem não pode ser nulo") 
    UserLanguage language,
    
    @NotBlank(groups=OnCreate.class, message = "O campo senha não pode estar em branco.") 
    String password
) {
    
}
