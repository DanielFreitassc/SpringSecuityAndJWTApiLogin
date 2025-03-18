package com.danielfreitassc.backend.models.user;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class UserEntity implements UserDetails{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(length=100)
    private String fullName;
    private String username;
    private String image;
    private LocalDate birthDate;
    private UserLanguage language;
    @Length(min = 6, max= 400, message = "A senha deve Conter no mínimo 6 caracteres.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).*$", message = "A senha deve conter pelo menos uma letra minúscula e uma letra maiúscula.")
    private String password; 
    private UserRole role;
    @CreationTimestamp
    private Timestamp createdAt;
    private int loginAttempts = 0;
    private LocalDateTime lockoutExpiration;

    public void lockAccount() {
        this.lockoutExpiration = LocalDateTime.now().plusMinutes(10);
    }

    public boolean isAccountLocked() {
        return lockoutExpiration != null && LocalDateTime.now().isBefore(lockoutExpiration);
    }

    public UserEntity(String fullName, String username, String image, UserLanguage language, LocalDate birthDate, String password, UserRole role) {
        this.fullName = fullName;
        this.username = username;
        this.image = image;
        this.language = language;
        this.birthDate = birthDate;
        this.password = password;
        this.role = (role != null) ? role : UserRole.USER;
    }

    public void incrementLoginAttempts() {
        this.loginAttempts++;
    }

    public void resetLoginAttempts() {
        this.loginAttempts = 0;
    }

   @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (this.role != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + this.role.getRole().toUpperCase()));
        }
        return authorities;
    }
    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return !isAccountLocked();
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}