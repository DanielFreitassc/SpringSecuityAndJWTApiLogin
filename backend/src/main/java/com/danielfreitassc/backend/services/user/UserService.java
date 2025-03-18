package com.danielfreitassc.backend.services.user;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.danielfreitassc.backend.dtos.user.UserRequestDto;
import com.danielfreitassc.backend.dtos.user.UserResponseDto;
import com.danielfreitassc.backend.mappers.user.UserMapper;
import com.danielfreitassc.backend.models.user.UserEntity;
import com.danielfreitassc.backend.models.user.UserRole;
import com.danielfreitassc.backend.repositories.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDto create(UserRequestDto userRequestDto) {
        checkDuplicateUsername(userRequestDto.username());

        String encryptedPassword =  new BCryptPasswordEncoder().encode(userRequestDto.password());
        UserEntity userEntity = userMapper.toEntity(userRequestDto);

        userEntity.setRole(UserRole.USER);
        userEntity.setPassword(encryptedPassword);
        return userMapper.toDto(userRepository.save(userEntity));
    }

    public Page<UserResponseDto> getAllUsers(Pageable pageable, String search) {
        return userRepository.findAll(pageable,search).map(userMapper::toDto);
    }

    public UserResponseDto getUserById(Long id) {
        return userMapper.toDto(findUserOrThrow(id));
    }
    
    public UserResponseDto patchUser(Long id,  UserRequestDto userRequestDto) {
       
        UserEntity userEntity = findUserOrThrow(id);

        userEntity.setRole(UserRole.USER);
        
        if (userRequestDto.fullName() != null && !userRequestDto.fullName().isBlank()) {
            userEntity.setFullName(userRequestDto.fullName());
        }
        if (userRequestDto.image() != null && !userRequestDto.image().isBlank()) {
            userEntity.setImage(userRequestDto.image());
        }
        if(userRequestDto.username() != null && !userRequestDto.username().isBlank()) {
            userEntity.setUsername(userRequestDto.username());
        }
        
        if (userRequestDto.birthDate() != userEntity.getBirthDate()) {
            userEntity.setBirthDate(userRequestDto.birthDate());
        }

        if (userRequestDto.language() != userEntity.getLanguage()) {
            userEntity.setLanguage(userRequestDto.language());
        }
    
        if (userRequestDto.password() != null && !userRequestDto.password().isBlank()) {
            String encryptedPassword = new BCryptPasswordEncoder().encode(userRequestDto.password());
            userEntity.setPassword(encryptedPassword);
        }
        
        return userMapper.toDto(userRepository.save(userEntity));
    }


    public UserResponseDto delete(Long id) {
        userRepository.delete(findUserOrThrow(id));
        return userMapper.toDto(findUserOrThrow(id));
    }


    // verifica se username já existe
    private void checkDuplicateUsername(String username) {
        if(userRepository.findByUsername(username) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Usuário já cadastrado na base de dados.");
        }
    }

    // Busca usuário ou retorna 404
    private UserEntity findUserOrThrow(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if(user.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Usuário não encontrado");
        return user.get();
    }
}
