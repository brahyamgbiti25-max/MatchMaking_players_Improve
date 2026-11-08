package com.example.MatchMaking_players.Controller;

import com.example.MatchMaking_players.Models.Request.AuthRequest;
import com.example.MatchMaking_players.Models.Request.ChangePasswordRequest;
import com.example.MatchMaking_players.Models.Request.EmailValidationRequest;
import com.example.MatchMaking_players.Models.Request.PasswordRequest;
import com.example.MatchMaking_players.Models.Responses.EmailValidationResponse;
import com.example.MatchMaking_players.Models.Responses.LogginResponse;
import com.example.MatchMaking_players.Models.Responses.MessageResponse;
import com.example.MatchMaking_players.Service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bra/auth")
public class UserAuthController {
   /* @Autowired
    private AuthService authService;*/

    private final AuthService authService;
    public UserAuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> singUpUser(@RequestBody @Valid AuthRequest authRequest){
        return ResponseEntity.ok(authService.singUpUSer(authRequest));
    }
    @GetMapping("/validate-email")
    public ResponseEntity<EmailValidationResponse> validateEmail(@RequestBody EmailValidationRequest email){
        EmailValidationResponse response=authService.validateEmail(email.getEmail());
        return ResponseEntity.ok(response);
    }
    @GetMapping("/verify-email")
    public ResponseEntity<MessageResponse> verifyEmail(@RequestParam String token){
        MessageResponse response =authService.verifyEmail(token);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody EmailValidationRequest validationRequest){
        String response=authService.loginUser(validationRequest.getEmail());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/resend-verification")
    public ResponseEntity<String> resendVerification(
            @RequestBody EmailValidationRequest validationRequest){
        String response=authService.resendVerification(validationRequest);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/forgot-password")
    public ResponseEntity<String> resetPassword(@RequestBody EmailValidationRequest emailValidationRequest){
        String Response = authService.sendTokenPassword(emailValidationRequest.getEmail());
        return ResponseEntity.ok(Response);
    }
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordRequest passwordRequest){
       String reponse= authService.resetPassword(passwordRequest);
        return  ResponseEntity.ok(reponse);
    }
    @PostMapping("/changed-password")
    public ResponseEntity<String> changePassword(Authentication authentication, @RequestBody ChangePasswordRequest changePasswordRequest){
        String email=authentication.getName();
        String response=authService.changePassword(email,changePasswordRequest.getNewPassword(),changePasswordRequest.getPasswordCurrent(),changePasswordRequest.getCommitCurrentPassword());
        return ResponseEntity.ok(response);
    }
    @GetMapping("current-user")
    public ResponseEntity<LogginResponse> currentUser(Authentication authentication){
        String email=authentication.getName();
        LogginResponse response=authService.currentUser(email);
        return ResponseEntity.ok(response);
    }

}
