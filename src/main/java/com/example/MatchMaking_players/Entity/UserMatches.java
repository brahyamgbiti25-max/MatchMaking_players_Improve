package com.example.MatchMaking_players.Entity;

import com.example.MatchMaking_players.Enums.MatchStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
@Table(name = "User_Matches")
public class UserMatches {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //foreign key to use a single table
    @ManyToOne(fetch =FetchType.LAZY,optional = false )
    @JoinColumn(name = "player_one",referencedColumnName = "id")
    private User playerOne;
    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "player_two",referencedColumnName = "id",nullable = true)
    private User playerTwo;
    @Column(nullable = false)
    private Float skill;
    @Column()
    private MatchStatus status;
    @Column(nullable = false)
    private Instant matchedAt;
}
