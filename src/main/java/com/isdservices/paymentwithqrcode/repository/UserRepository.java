package com.isdservices.paymentwithqrcode.repository;


import com.isdservices.paymentwithqrcode.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
