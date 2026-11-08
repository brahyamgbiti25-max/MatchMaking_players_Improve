package com.example.MatchMaking_players.Service;

public interface EmailService {
    void sendVerificationEmail(String toEmail,String token);

    void resendVerification(String email, String verificationToken);

    void sendVerificationPasssword(String email, String passwordResetToken);
}
