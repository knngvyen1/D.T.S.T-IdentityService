package com.example.usermanagement.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";

    private JwtService jwtService;

    @Autowired
    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filter) throws ServletException, IOException {
        String token = getTokenFromHeader(req);

        if(token != null && jwtService.isTokenValid(token)){
            String email = jwtService.getEmailFromToken(token);
            Collection<? extends GrantedAuthority> roles = jwtService.getRoleFromToken(token);

            Authentication authentication = new UsernamePasswordAuthenticationToken(email, token, roles);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filter.doFilter(req, res);
    }

    private String getTokenFromHeader(HttpServletRequest req) {
        String token = req.getHeader(AUTHORIZATION_HEADER);

        if (token == null || !token.startsWith(TOKEN_PREFIX)) {
            return null;
        }

        return token.replace(TOKEN_PREFIX, "");
    }
}
