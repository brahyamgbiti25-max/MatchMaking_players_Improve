package com.example.MatchMaking_players.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private  JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response
            , FilterChain filterChain) throws ServletException, IOException {
        String jwt=extractJwtToken(request);
        String userName=null;
        if(jwt!=null){
            userName=jwtUtil.getUsernameFrontToken(jwt);
        }
        if(shouldProcessAuthentication(userName)){
            ProcessAuthentication(request,jwt,userName);
        }
        filterChain.doFilter(request,response);
    }

    private String extractJwtToken(HttpServletRequest request) {
        final String authorizationReader=request.getHeader("Authorization");
        final String requestURI=request.getRequestURI();
        if(authorizationReader != null && authorizationReader.startsWith("Bearer")){
            return authorizationReader.substring(7);
        }
        return null;
    }
    private boolean shouldProcessAuthentication(String userName) {
        return userName!=null && SecurityContextHolder.getContext().getAuthentication()==null;
    }
    private void ProcessAuthentication(HttpServletRequest request, String jwt, String userName) {
        System.out.println("Token is valid"+ jwtUtil.validateToken(jwt));
        if (jwtUtil.validateToken(jwt)){
            UserDetails userDetails=createUserDetailsFromToken(jwt,userName);
            setAuthenticationInContext(request,userDetails);
        }
    }

    private UserDetails createUserDetailsFromToken(String jwt, String userName) {
        String role=jwtUtil.getRoleFromToken(jwt);

        return User.builder()
                .username(userName)
                .password("")
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(("ROLE_"+role))))
                .build();
    }


    private void setAuthenticationInContext(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }


}
