package com.example.MatchMaking_players.Service;

import com.example.MatchMaking_players.Models.Request.AuthRequest;
import com.example.MatchMaking_players.Models.Request.EmailValidationRequest;
import com.example.MatchMaking_players.Models.Request.PasswordRequest;
import com.example.MatchMaking_players.Models.Responses.EmailValidationResponse;
import com.example.MatchMaking_players.Models.Responses.LogginResponse;
import com.example.MatchMaking_players.Models.Responses.MessageResponse;

public interface AuthService {
    MessageResponse singUpUSer(AuthRequest authRequest);

    EmailValidationResponse validateEmail(String email);

    MessageResponse verifyEmail(String token);

   String loginUser(String email);

    String resendVerification(EmailValidationRequest validationRequest);

    String sendTokenPassword(String email);

    String resetPassword(PasswordRequest passwordRequest);

    String changePassword(String email, String newPassword, String passwordCurrent, String commitCurrentPassword);

    LogginResponse currentUser(String email);
}
