package com.danielfreitassc.backend.repositories.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import com.danielfreitassc.backend.models.user.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
    UserDetails findByUsername(String username);

    @Query("SELECT u FROM UserEntity u WHERE UPPER(u.fullName) LIKE CONCAT('%',UPPER(:search),'%') ORDER BY createdAt DESC")
    Page<UserEntity> findAll(Pageable pageable, String search);
}

