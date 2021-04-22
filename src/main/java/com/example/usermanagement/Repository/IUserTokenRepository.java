package com.example.usermanagement.Repository;

import com.example.usermanagement.Domain.Model.User;
import com.example.usermanagement.Domain.Model.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserTokenRepository extends JpaRepository<UserToken, Long> {
    Optional<UserToken> getByEmail(String username);
    Optional<UserToken> getByToken(String token);

}
