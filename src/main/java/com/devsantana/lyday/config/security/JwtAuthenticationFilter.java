package com.devsantana.lyday.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtTokenService jwtTokenService;
    private final TokenBlackListService blackListService;

    public JwtAuthenticationFilter(UserDetailsService userDetailsService,
                                   JwtTokenService jwtTokenService,
                                   TokenBlackListService blackListService){
        this.userDetailsService = userDetailsService;
        this.jwtTokenService = jwtTokenService;
        this.blackListService = blackListService;
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        String path = request.getRequestURI();
        return path.startsWith("/auth") || path.startsWith("/error");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
        throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        //============================================ PRINT no LOG
        System.out.println("JWT EXECUTANDO " + request.getRequestURI());
        //============================================ PRINT no LOG

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (blackListService.isBlackListed(token)){
                filterChain.doFilter(request, response);
                return;
            }

            if (jwtTokenService.tokenValid(token) &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                String username = jwtTokenService.getUsername(token);

                String role = jwtTokenService.getRole(token);

                var authorities = List.of(new SimpleGrantedAuthority(role));

                UserDetails userDetails =userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                authorities
                        );
                authentication
                        .setDetails(
                        new
                                WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
