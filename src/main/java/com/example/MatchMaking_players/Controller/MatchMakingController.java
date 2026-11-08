package com.example.MatchMaking_players.Controller;

import com.example.MatchMaking_players.Models.Responses.MessageResponse;
import com.example.MatchMaking_players.Service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/matchmaking")
public class MatchMakingController {
    @Autowired
    private MatchService matchService;
    //well here I'm gonna create an endpoint to create the matchmaking
    //well in this Principal object is like Authentication but this jus gives you the name and user that
    //currently is doing the request and authentication givest you all the congtext of the user
    @PostMapping("/find")
    public ResponseEntity<MessageResponse> findMatch(Principal principal){
        // principal.getName() securely extracts the username from your validated JWT filter
        MessageResponse response=matchService.findOrCreateMatch(principal.getName());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/cancel")
    public ResponseEntity<MessageResponse> cancelMatchMaking(Principal principal){
        // principal.getName() securely extracts the username from your validated JWT filter
        MessageResponse response=matchService.cancelMatchMaking(principal.getName());
        return ResponseEntity.ok(response);
    }

}
