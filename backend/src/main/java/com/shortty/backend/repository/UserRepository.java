package com.shortty.backend.repository;

import com.shortty.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsernameOrEmail(String username, String email);
//    boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);
}
