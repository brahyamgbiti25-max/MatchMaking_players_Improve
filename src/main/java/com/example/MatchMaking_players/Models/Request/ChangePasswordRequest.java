package com.example.MatchMaking_players.Models.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String passwordCurrent;
    private String commitCurrentPassword;
    private String newPassword;
}
