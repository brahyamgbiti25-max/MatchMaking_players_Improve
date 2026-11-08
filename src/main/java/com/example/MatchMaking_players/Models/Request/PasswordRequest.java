package com.example.MatchMaking_players.Models.Request;

import lombok.Data;

@Data
public class PasswordRequest {

    private String passwordToChange;
    private String token;
}
