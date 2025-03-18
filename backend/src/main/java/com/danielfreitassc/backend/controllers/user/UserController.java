package com.danielfreitassc.backend.controllers.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.danielfreitassc.backend.configurations.OnCreate;
import com.danielfreitassc.backend.dtos.user.UserRequestDto;
import com.danielfreitassc.backend.dtos.user.UserResponseDto;
import com.danielfreitassc.backend.services.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto create(@RequestBody @Validated(OnCreate.class) UserRequestDto userRequestDto) {
        return  userService.create(userRequestDto);
    }

    @GetMapping
    public Page<UserResponseDto> getAllUsers(Pageable pageable, @RequestParam(value="search",required=false,defaultValue="") String search) {
        return userService.getAllUsers(pageable, search);
    }

    @GetMapping("/{id}")
    public UserResponseDto getById(@PathVariable Long id) {
        return  userService.getUserById(id);
    }

    @PatchMapping("/{id}")
    public UserResponseDto update(@PathVariable Long id, @RequestBody @Valid UserRequestDto userRequestDto) {
        return  userService.patchUser(id, userRequestDto);
    }

    @DeleteMapping("/{id}")
    public UserResponseDto delete(@PathVariable Long id) {
        return  userService.delete(id);
    }
}
