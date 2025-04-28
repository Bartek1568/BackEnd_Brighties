package com.brighties.userservice.repository;

import com.brighties.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface BaseUserRepository<T extends User> extends JpaRepository<T,Long> {

    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(Integer phoneNumber);

    Optional<T> findByEmail(String email);


}
