package com.example.MatchMaking_players.Servicelmpl;

import com.example.MatchMaking_players.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailAuthenticationServerlmpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${app.frontend.url}")
    private  String frontUrl;
    @Value("${spring.mail.username}")
    private  String fronEmail;

    @Override
    public void sendVerificationEmail(String toEmail, String token) {
        try{
            SimpleMailMessage message =new SimpleMailMessage();
            message.setFrom(fronEmail);
            message.setTo(toEmail);
            message.setSubject("Talent_isn't_requerid");
            String verificationLink=frontUrl+"/verify-email?token="+token;
            String emailBody=
                    "Welcome to nefflix clone!\n\n"
                            +"thanks you for registering. Please verify your email address by clicking the link below\n\n"
                            + verificationLink
                            +"\n\n"
                            +"This link will expire in 24 hours. \n\n"
                            +"If you didn't create this account , ignore this eamil. \n\n"
                            +"Best regards. \n"
                            +"Amazon clone team";
            message.setText(emailBody);
            mailSender.send(message);
        }catch (Exception ex){
            throw new RuntimeException("Failed to send verification email");
        }
    }

    @Override
    public void resendVerification(String toEmail, String token) {
        try{
            SimpleMailMessage message =new SimpleMailMessage();
            message.setFrom(fronEmail);
            message.setTo(toEmail);
            message.setSubject("Talent_isn't_requerid");
            String verificationLink=frontUrl+"/verify-email?token="+token;
            String emailBody=
                    "Welcome to MatchMaking!\n\n"
                            +"thanks you for registering. Please verify your email address by clicking the link below\n\n"
                            + verificationLink
                            +"\n\n"
                            +"This link will expire in 24 hours. \n\n"
                            +"If you didn't create this account , ignore this eamil. \n\n"
                            +"Best regards. \n"
                            +"MatchMaking team";
            message.setText(emailBody);
            mailSender.send(message);
        }catch (Exception ex){
            throw new RuntimeException("Failed to send verification email");
        }
    }

    @Override
    public void sendVerificationPasssword(String toEmail, String passwordResetToken) {

        try{
            SimpleMailMessage message =new SimpleMailMessage();
            message.setFrom(fronEmail);
            message.setTo(toEmail);
            message.setSubject("Talent_isn't_requerid");
            String verificationLink=frontUrl+"/verify-email?token="+passwordResetToken;
            String emailBody=
                    "Welcome to MatchMaking!\n\n"
                            +"here is the token to change your password\n\n"
                            + verificationLink
                            +"\n\n"
                            +"This link will expire in 1 hour. \n\n"
                            +"If you didn't want this , ignore this eamil. \n\n"
                            +"Best regards. \n"
                            +"MatchMaking team";
            message.setText(emailBody);
            mailSender.send(message);
        }catch (Exception ex){
            throw new RuntimeException("Failed to send verification email");
        }
    }
}
