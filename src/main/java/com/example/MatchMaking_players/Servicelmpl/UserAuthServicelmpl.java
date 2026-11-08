package com.example.MatchMaking_players.Servicelmpl;

import com.example.MatchMaking_players.Dao.UserRepository;
import com.example.MatchMaking_players.Entity.User;
import com.example.MatchMaking_players.Enums.Role;
import com.example.MatchMaking_players.Models.Request.AuthRequest;
import com.example.MatchMaking_players.Models.Request.EmailValidationRequest;
import com.example.MatchMaking_players.Models.Request.PasswordRequest;
import com.example.MatchMaking_players.Models.Responses.EmailValidationResponse;
import com.example.MatchMaking_players.Models.Responses.LogginResponse;
import com.example.MatchMaking_players.Models.Responses.MessageResponse;
import com.example.MatchMaking_players.Security.JwtAuthenticationFilter;
import com.example.MatchMaking_players.Security.JwtUtil;
import com.example.MatchMaking_players.Service.AuthService;
import com.example.MatchMaking_players.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class UserAuthServicelmpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private JwtUtil jwtUtil;
    //well  I tried to understand the Response entity and I understood that is better use
    //classes to messages than uses I simple String and also you can reuse this class for every message in
    //your proyect
    @Override
    public MessageResponse singUpUSer(AuthRequest authRequest) {
        if(userRepository.existsByEmail(authRequest.getEmail())){
            throw new RuntimeException("this user actually have an acccount so try with other email");
        }else{
            User user = new User();
            user.setVerificationToken(UUID.randomUUID().toString());
            user.setUserName(authRequest.getUserName());
            user.setActive(true);
            user.setEmail(authRequest.getEmail());
            user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
            user.setCreatedAt(Instant.now());
            user.setRole(Role.USER);
            user.setSkillRate(1500f);
            emailService.sendVerificationEmail(authRequest.getEmail(), user.getVerificationToken() );
            userRepository.save(user);

        }

        return new MessageResponse("account created successfull");
    }

    @Override
    public EmailValidationResponse validateEmail(String email) {
        System.out.println(email);
      if(userRepository.existsByEmail(email)){
          return  new EmailValidationResponse(false,true);
      }
        return new EmailValidationResponse(true,false);
    }

    @Override
    public MessageResponse verifyEmail(String token) {
        System.out.println(token);
        User user=userRepository.findByVerificationToken(token)
                .orElseThrow(()->new RuntimeException("this token is fake please check out in your personal Email"));
        user.setVerificationToken(null);
        user.setEmailVerified(true);
        userRepository.save(user);
    return new MessageResponse("now you can loggin");
    }

    @Override
    public String loginUser(String email) {
        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("this user doesn't exists"));
        if(!user.isEmailVerified()){
            throw new RuntimeException("this email isn't verifique so go to check out in your email");
        }

        return jwtUtil.generateToken(user.getEmail(),user.getRole().name());
    }

    @Override
    public String resendVerification(EmailValidationRequest validationRequest) {
       User user= userRepository.findByEmail(validationRequest.getEmail())
                .orElseThrow(()->new RuntimeException("this user doesn't exits"));
        user.setVerificationToken(UUID.randomUUID().toString());
        userRepository.save(user);
        emailService.resendVerification(validationRequest.getEmail(),user.getVerificationToken());
        return "verification resent so check out your email again";
    }

    @Override
    public String sendTokenPassword(String email) {
        User user= userRepository.findByEmail(email)
                        .orElseThrow(()->new RuntimeException("this user doesn't exist"));
        user.setPasswordResetToken(UUID.randomUUID().toString());
        user.setPasswordResetTokenExpiry(Instant.now().plusSeconds(36000));
        userRepository.save(user);
        emailService.sendVerificationPasssword(email,user.getPasswordResetToken());
        return "check out your email again for this change";
    }

    @Override
    public String resetPassword(PasswordRequest passwordRequest) {
       User user= userRepository.findByPasswordResetToken(passwordRequest.getToken())
               .orElseThrow(()->new RuntimeException("this token is ivaliable so you need to check your Email"));
       if(user.getPasswordResetTokenExpiry()==null || user.getPasswordResetTokenExpiry().isBefore(Instant.now())){
           throw new RuntimeException("your token of now is invalid so try with others");
       }
       user.setPasswordResetTokenExpiry(null);
       user.setPasswordResetToken(null);
       user.setPassword(passwordEncoder.encode(passwordRequest.getPasswordToChange()));
       userRepository.save(user);
        return "you password already has been changed";
    }

    @Override
    public String changePassword(String email, String newPassword, String passwordCurrent, String commitCurrentPassword) {
        System.out.println("email"+email);
        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("this user doesn't exist"));
        if(!passwordCurrent.equals(commitCurrentPassword)){
            throw new RuntimeException("these password don't match");
        }
        if(!passwordEncoder.matches(passwordCurrent,user.getPassword())){
            throw new RuntimeException("these password don't match");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return "password changed successfully";
    }

    @Override
    public LogginResponse currentUser(String email) {
        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("this user actually doesn't exist"));
        return new LogginResponse(user.getUserName(),user.getRole().name(),user.getSkillRate(),user.getEmail());
    }

    //tomorrow I have to create another endpoit almost for myselft to understand this

}
