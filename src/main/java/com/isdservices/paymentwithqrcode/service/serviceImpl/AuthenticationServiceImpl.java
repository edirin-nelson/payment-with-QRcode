package com.isdservices.paymentwithqrcode.service.serviceImpl;


import com.isdservices.paymentwithqrcode.dto.request.LoginRequest;
import com.isdservices.paymentwithqrcode.dto.response.LoginResponse;
import com.isdservices.paymentwithqrcode.exception.UserAccountDisabledException;
import com.isdservices.paymentwithqrcode.exception.UserNotFoundException;
import com.isdservices.paymentwithqrcode.model.JwtToken;
import com.isdservices.paymentwithqrcode.model.User;
import com.isdservices.paymentwithqrcode.repository.JwtTokenRepository;
import com.isdservices.paymentwithqrcode.repository.UserRepository;
import com.isdservices.paymentwithqrcode.security.JwtService;
import com.isdservices.paymentwithqrcode.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final JwtTokenRepository jwtTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    @Override
    public LoginResponse authenticateUser(LoginRequest request) throws UserNotFoundException, UserAccountDisabledException {
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(()-> new UserNotFoundException("User with email: " +request.getEmail() +" not found"));
        user.setEnabled(true);
        userRepository.save(user);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        revokeToken(user);
        var jwtToken = jwtService.generateToken(user);
        saveToken(user,jwtToken);

        return LoginResponse.builder()
                .userID(user.getId().toString())
                .token(jwtToken)
                .expiredAt(jwtService.extractExpiration(jwtToken))
                .firstName(user.getFirstName())
                .userType(user.getRole().toString())
                .build();
    }

    private void saveToken(User user, String jwtToken) {
        JwtToken token = JwtToken.builder()
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .user(user)
                .build();
        jwtTokenRepository.save(token);
    }

    private void revokeToken(User user){
        var validTokenByUser= jwtTokenRepository.findTokenByUserAndExpiredIsFalseAndRevokedIsFalse(user);

        if(validTokenByUser.isEmpty()) return;

        validTokenByUser.forEach(token->{
            token.setRevoked(true);
            token.setExpired(true);
        });

        jwtTokenRepository.saveAll(validTokenByUser);
    }


}
