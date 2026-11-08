package com.example.MatchMaking_players.Servicelmpl;

import com.example.MatchMaking_players.Dao.UserMatchesRepository;
import com.example.MatchMaking_players.Dao.UserRepository;
import com.example.MatchMaking_players.Entity.User;
import com.example.MatchMaking_players.Entity.UserMatches;
import com.example.MatchMaking_players.Enums.MatchStatus;
import com.example.MatchMaking_players.Models.Responses.MessageResponse;
import com.example.MatchMaking_players.Service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class MatchServicelmpl implements MatchService {
    @Autowired
    private UserMatchesRepository userMatchesRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    @Transactional
    public MessageResponse findOrCreateMatch(String username) {
        System.out.println("user?"+username);
        User currentUser=userRepository.findByEmail(username)
                .orElseThrow(()->new RuntimeException("User not found"));
        boolean alreadyInQueueOrMatch=userMatchesRepository.existsByPlayerOneAndStatusOrPlayerTwoAndStatus
                (currentUser, MatchStatus.SEARCHING,currentUser,MatchStatus.ACTIVE);
        if(alreadyInQueueOrMatch){
            throw new RuntimeException("you are already in matchmaking queue or an active match");
        }
        float currentSkill = currentUser.getSkillRate();
        float skillWindow= 150.0f;
        float minSkill=currentSkill-skillWindow;
        float maxSkill= currentSkill+skillWindow;
        Optional<UserMatches> waitingQueueOpt = userMatchesRepository
                .findFirstByStatusAndPlayerOneNotAndPlayerOneSkillRateBetweenOrderByMatchedAtAsc(
                        MatchStatus.SEARCHING,currentUser,minSkill,maxSkill
                );
        if(waitingQueueOpt.isPresent()){
            UserMatches matches=waitingQueueOpt.get();
            matches.setPlayerTwo(currentUser);
            matches.setStatus(MatchStatus.ACTIVE);
            userMatchesRepository.save(matches);
            return new MessageResponse("Match found! Opponent: "+matches.getPlayerOne().getUserName());
        }else {
            UserMatches newQueueuTicket=new UserMatches();
            newQueueuTicket.setPlayerOne(currentUser);
            newQueueuTicket.setStatus(MatchStatus.SEARCHING);
            newQueueuTicket.setMatchedAt(Instant.now());
            userMatchesRepository.save(newQueueuTicket);
            return  new MessageResponse("Added to queue. Searching for an opponent");
        }
    }

    @Override
    public MessageResponse cancelMatchMaking(String name) {
        User currentUser= userRepository.findByEmail(name)
                .orElseThrow(()->new RuntimeException("user not found"));
        MatchStatus ticket= userMatchesRepository.findFirstByStatusAndPlayerOneNotOrderByMatchedAtAsc(MatchStatus.ACTIVE,currentUser);
        return new MessageResponse("Matchmaking cancelled successfully");
    }
}
