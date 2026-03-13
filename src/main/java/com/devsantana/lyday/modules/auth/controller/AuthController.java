package com.devsantana.lyday.modules.auth.controller;

import com.devsantana.lyday.config.security.JwtTokenService;
import com.devsantana.lyday.config.security.TokenBlackListService;
import com.devsantana.lyday.modules.auth.dto.LoginRequestDto;
import com.devsantana.lyday.modules.auth.dto.LoginResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final TokenBlackListService blackListService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenService jwtTokenService,
                          TokenBlackListService blackListService){
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.blackListService = blackListService;
    }

    @PostMapping("/login")
    public LoginResponseDto login(@Valid @RequestBody LoginRequestDto dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        dto.getPassword()
                )
        );
        String token =
                jwtTokenService.generateToken(authentication);
        return new LoginResponseDto(token);
    }
    @PostMapping("/logout")
    public void logout(HttpServletRequest request){

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")){

            String token = authHeader.substring(7);

            blackListService.blacklistToken(token);
        }
    }
}