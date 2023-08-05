package com.isdservices.paymentwithqrcode.controller;

import com.isdservices.paymentwithqrcode.model.Wallet;
import com.isdservices.paymentwithqrcode.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final WalletService walletService;

    @GetMapping("/wallet-details")
    public ResponseEntity<Wallet> getWallet(){
        return walletService.getWallet();
    }
}
