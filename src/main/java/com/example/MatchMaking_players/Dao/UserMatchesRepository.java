package com.example.MatchMaking_players.Dao;

import com.example.MatchMaking_players.Entity.User;
import com.example.MatchMaking_players.Entity.UserMatches;
import com.example.MatchMaking_players.Enums.MatchStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface UserMatchesRepository extends JpaRepository<UserMatches,Long> {

    boolean existsByPlayerOneAndStatusOrPlayerTwoAndStatus(User currentUser, MatchStatus matchStatus, User currentUser1, MatchStatus matchStatus1);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<UserMatches> findFirstByStatusAndPlayerOneNotAndPlayerOneSkillRateBetweenOrderByMatchedAtAsc(MatchStatus matchStatus, User currentUser, float minSkill, float maxSkill);

    MatchStatus findFirstByStatusAndPlayerOneNotOrderByMatchedAtAsc(MatchStatus matchStatus, User currentUser);
}
