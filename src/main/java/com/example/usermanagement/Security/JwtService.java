package com.example.usermanagement.Security;

import com.example.usermanagement.Domain.Model.RoleType;
import com.example.usermanagement.Domain.Model.User;
import com.example.usermanagement.Domain.Model.UserToken;
import com.example.usermanagement.Repository.IUserRepository;
import com.example.usermanagement.Repository.IUserRoleRepository;
import com.example.usermanagement.Repository.IUserTokenRepository;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String JWTSECRET;

    @Value("${jwt.duration}")
    private long JWTDURATION;

    private IUserRepository userRepository;
    private IUserRoleRepository roleRepository;
    private IUserTokenRepository userTokenRepository;

    @Autowired
    public JwtService(IUserRepository userRepository, IUserRoleRepository userRoleRepository, IUserTokenRepository userTokenRepository) {
        this.userRepository = userRepository;
        this.roleRepository = userRoleRepository;
        this.userTokenRepository = userTokenRepository;
    }

    public UserToken createToken(User user) {

        // Create the claims for the token
        Claims claims = Jwts.claims();
        claims.put("sub", user.getEmail());
        claims.put("jti", UUID.randomUUID().toString());
        claims.put("userID", user.getId());
        claims.put("ROLE", user.getRole().getRoleType().name());

        // Create the token with the values above
        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWTDURATION))
                .signWith(SignatureAlgorithm.HS256, JWTSECRET)
                .compact();

        // Generate a random refresh token for this access token
        String refreshToken = UUID.randomUUID().toString();

        // Store the token in the database
        UserToken userToken = new UserToken(user.getEmail(), accessToken, refreshToken);

        // Return the token
        return userTokenRepository.save(userToken);
    }

    public boolean isTokenValid(String token) {
        try {

            // Check if token is valid by signature
            Jwts.parser()
                    .setSigningKey(JWTSECRET)
                    .parseClaimsJws(token);

            Optional<User> optionalUser = userRepository.findByEmail(getEmailFromToken(token));

            // Get the email from the token and check if the email exists in the database
            if (optionalUser.isEmpty()) {
                return false;
            }

            User user = optionalUser.get();

            // Check if user role matches the role from the token
            String userRole = user.getRole().getRoleType().name();
//            String tokenRole = getRoleFromToken(token).getRoleType().name();
            Collection<? extends GrantedAuthority> tokenRole = getRoleFromToken(token);

            if (!userRole.equals(tokenRole)) {
                return false;
            }

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        return Jwts.parser().setSigningKey(JWTSECRET).parseClaimsJws(token).getBody().getSubject();

    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWTSECRET)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.get("userID").toString());
    }


    public Collection<? extends GrantedAuthority> getRoleFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWTSECRET)
                .parseClaimsJws(token)
                .getBody();

        RoleType roleType = RoleType.valueOf(claims.get("ROLE", String.class));
        roleRepository.getByRoleType(roleType);
        return Arrays.stream(claims.get("ROLE").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
