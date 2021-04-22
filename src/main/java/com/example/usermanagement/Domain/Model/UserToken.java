package com.example.usermanagement.Domain.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user_token")
@NoArgsConstructor
@Setter
@Getter
public class UserToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;

    private String token;

    private String refreshToken;

    public UserToken(String email, String token, String refreshToken){
        this.email = email;
        this.token = token;
        this.refreshToken = refreshToken;
    }
}
