package com.example.MatchMaking_players.Entity;

import com.example.MatchMaking_players.Enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Data
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role=Role.USER;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "Skill_rate")
    private Float skillRate;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private boolean active=true;
    @Column
    private boolean emailVerified=false;
    @Column(unique = true)
    private String verificationToken;
    @Column
    private Instant verificationTokenExpiry;
    @Column
    private String passwordResetToken;
    @Column
    private Instant passwordResetTokenExpiry;
    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private Instant createdAt;
    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updateAt;
}
