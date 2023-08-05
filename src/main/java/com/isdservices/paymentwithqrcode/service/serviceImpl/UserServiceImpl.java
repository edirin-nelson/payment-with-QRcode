package com.isdservices.paymentwithqrcode.service.serviceImpl;


import com.isdservices.paymentwithqrcode.dto.request.CreateAccountRequest;
import com.isdservices.paymentwithqrcode.dto.request.RegisterRequest;
import com.isdservices.paymentwithqrcode.dto.response.CreateAccountResponse;
import com.isdservices.paymentwithqrcode.enums.Role;
import com.isdservices.paymentwithqrcode.exception.UserAlreadyExistsException;
import com.isdservices.paymentwithqrcode.flutterAPI.AccountCreationData;
import com.isdservices.paymentwithqrcode.flutterAPI.FlutterURLs;
import com.isdservices.paymentwithqrcode.flutterAPI.RestTemplateUtils;
import com.isdservices.paymentwithqrcode.model.User;
import com.isdservices.paymentwithqrcode.model.Wallet;
import com.isdservices.paymentwithqrcode.repository.UserRepository;
import com.isdservices.paymentwithqrcode.repository.WalletRepository;
import com.isdservices.paymentwithqrcode.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;
    private final RestTemplateUtils header;
    private final WalletRepository walletRepo;

    @Override
    public User registerUser(RegisterRequest request) throws UserAlreadyExistsException {

        Optional<User> user = userRepository.findByEmail(request.getEmail());

        if(user.isPresent()){
            throw new UserAlreadyExistsException("User with email "+request.getEmail() + " already exists.");
        }

        User newUser = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .enabled(false)
                .bvn(request.getBvn())
                .role(Role.USER)
                .pin(passwordEncoder.encode(request.getPin()))
                .build();

        User u = userRepository.save(newUser);
        createAccount(u);

        return u;
    }

    private void createAccount( User user){
       var accountCreation = CreateAccountRequest.builder()
               .email(user.getEmail())
               .is_permanent(true)
               .bvn(user.getBvn())
               .tx_ref("VA12")
               .phonenumber(user.getPhoneNumber())
               .firstname(user.getFirstName())
               .lastname(user.getLastName())
               .narration("account creation for " +user.getFirstName() +" "+user.getLastName())
               .build();


        HttpEntity<CreateAccountRequest> request = new HttpEntity<>(accountCreation, header.getHeaders());
        String url = FlutterURLs.BASE_URL + FlutterURLs.CREATE_ACCOUNT;

        ResponseEntity<CreateAccountResponse> response = restTemplate.postForEntity(url, request, CreateAccountResponse.class );
        CreateAccountResponse resp = response.getBody();

        if(resp.getStatus().equals("success") && resp.getData().getResponse_code().equals("02")) {
            log.info(resp.toString());
            createWallet(resp.getData(), user.getEmail());
        }

    }

    private void createWallet(AccountCreationData resp, String email) {
        User newUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        log.info("newUser" + newUser.toString());

        var newWallet = Wallet.builder()
                .accountBalance(resp.getAmount())
                .accountNumber(resp.getAccount_number())
                .bankName(resp.getBank_name())
                .created_at(resp.getCreated_at())
                .expiry_date(resp.getExpiry_date())
                .flw_ref(resp.getFlw_ref())
                .frequency(resp.getFrequency())
                .order_ref(resp.getOrder_ref())
                .user(newUser)
                .build();
        log.info("wallet" + newWallet.toString());

        walletRepo.save(newWallet);
        newUser.setWallet(newWallet);
        userRepository.save(newUser);
    }
}
