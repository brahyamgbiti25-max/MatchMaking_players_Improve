package com.example.MatchMaking_players.Models.Responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailValidationResponse {
    private boolean avaliable;
    private boolean exists;
}
