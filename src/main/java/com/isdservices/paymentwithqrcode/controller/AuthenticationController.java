package com.isdservices.paymentwithqrcode.controller;


import com.isdservices.paymentwithqrcode.dto.request.LoginRequest;
import com.isdservices.paymentwithqrcode.dto.request.RegisterRequest;
import com.isdservices.paymentwithqrcode.dto.response.LoginResponse;
import com.isdservices.paymentwithqrcode.exception.UserAccountDisabledException;
import com.isdservices.paymentwithqrcode.exception.UserAlreadyExistsException;
import com.isdservices.paymentwithqrcode.exception.UserNotFoundException;
import com.isdservices.paymentwithqrcode.model.User;
import com.isdservices.paymentwithqrcode.service.AuthenticationService;
import com.isdservices.paymentwithqrcode.service.UserService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) throws UserNotFoundException, UserAccountDisabledException {
        return ResponseEntity.ok(authenticationService.authenticateUser(request));
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody @Valid @NonNull RegisterRequest request) throws UserAlreadyExistsException, UserNotFoundException {
        return ResponseEntity.ok(userService.registerUser(request));
    }

}
