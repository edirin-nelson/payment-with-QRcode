package com.isdservices.paymentwithqrcode.service.serviceImpl;


import com.isdservices.paymentwithqrcode.repository.JwtTokenRepository;
import com.isdservices.paymentwithqrcode.repository.UserRepository;
import com.isdservices.paymentwithqrcode.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutHandler {
    private  final JwtService jwtUtils;
    private final UserRepository employeeRepository;
    private final JwtTokenRepository jwtTokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);
        userEmail = jwtUtils.extractUsername(jwt);

        var user = employeeRepository.findByEmail(userEmail)
                .orElse(null); // throw a null if the user is not found.
        var token = jwtTokenRepository.findByToken(jwt)
                .orElse(null);
        if (token == null && user == null) {
            return; // does nothing if either one is null
        }
        assert user != null; // this make sure the user is not null
        employeeRepository.save(user);

        assert token != null; //this make sure the token is not null
        token.setExpired(true);
        token.setRevoked(true);
        jwtTokenRepository.save(token);
    }
}
