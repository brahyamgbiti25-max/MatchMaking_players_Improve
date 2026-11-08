package com.example.MatchMaking_players.Dao;

import com.example.MatchMaking_players.Entity.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

     boolean existsByEmail(@Email(message = "must be email nothing else") String email);

    Optional<User> findByVerificationToken(String token);

    Optional<User> findByPasswordResetToken(String token);

    Optional<User> findByUserName(String username);
}
