package com.isdservices.paymentwithqrcode.service.serviceImpl;

import com.isdservices.paymentwithqrcode.model.User;
import com.isdservices.paymentwithqrcode.model.Wallet;
import com.isdservices.paymentwithqrcode.repository.UserRepository;
import com.isdservices.paymentwithqrcode.repository.WalletRepository;
import com.isdservices.paymentwithqrcode.service.WalletService;
import com.isdservices.paymentwithqrcode.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepo;
    private final UserRepository userRepo;
    private final SecurityUtils securityUtils;

    @Override
    public ResponseEntity<Wallet> getWallet() {

        String email = securityUtils.getCurrentUserDetails().getUsername();
        User user = userRepo.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("user not found"));

        return ResponseEntity.ok(walletRepo.findByUser(user)) ;
    }
}
