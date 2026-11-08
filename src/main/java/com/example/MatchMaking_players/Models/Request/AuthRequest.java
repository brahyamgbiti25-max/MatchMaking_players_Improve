package com.example.MatchMaking_players.Models.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

@Data
public class AuthRequest {
    String userName;
    @NotBlank(message = "can't empty this field")
    String password;
    float skillRate;
    @Email(message = "must be email nothing else")
    String email;
}
