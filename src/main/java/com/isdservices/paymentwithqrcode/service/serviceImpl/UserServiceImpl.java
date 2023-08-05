package com.isdservices.paymentwithqrcode.service.serviceImpl;


import com.isdservices.paymentwithqrcode.dto.request.RegisterRequest;
import com.isdservices.paymentwithqrcode.enums.Role;
import com.isdservices.paymentwithqrcode.exception.UserAlreadyExistsException;
import com.isdservices.paymentwithqrcode.entity.User;
import com.isdservices.paymentwithqrcode.repository.UserRepository;
import com.isdservices.paymentwithqrcode.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(RegisterRequest request) throws UserAlreadyExistsException {

        Optional<User> user = userRepository.findByEmail(request.getEmail());

        if(user.isPresent()){
            throw new UserAlreadyExistsException(
                    "User with email "+request.getEmail() + " already exists.");
        }

        User newUser = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .enabled(false)
                .role(Role.USER)
                .pin(passwordEncoder.encode(request.getPin()))
                .build();

        return userRepository.save(newUser);
    }

}
