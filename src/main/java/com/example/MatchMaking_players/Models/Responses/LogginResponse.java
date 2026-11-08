package com.example.MatchMaking_players.Models.Responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogginResponse {
    private String userName;
    private String role;
    private Float skillRate;
    private String email;
}
