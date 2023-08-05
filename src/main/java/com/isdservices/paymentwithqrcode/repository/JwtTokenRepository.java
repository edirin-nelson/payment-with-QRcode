package com.isdservices.paymentwithqrcode.repository;


import com.isdservices.paymentwithqrcode.model.JwtToken;
import com.isdservices.paymentwithqrcode.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JwtTokenRepository extends JpaRepository<JwtToken, Integer> {
    Optional<JwtToken> findByToken(String token);

    List<JwtToken> findTokenByUserAndExpiredIsFalseAndRevokedIsFalse(User user);
}
