package com.example.MatchMaking_players.Service;

import com.example.MatchMaking_players.Models.Responses.MessageResponse;

public interface MatchService {
    MessageResponse findOrCreateMatch(String name);

    MessageResponse cancelMatchMaking(String name);
}
