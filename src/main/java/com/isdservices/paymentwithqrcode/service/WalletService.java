package com.isdservices.paymentwithqrcode.service;

import com.isdservices.paymentwithqrcode.model.Wallet;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface WalletService {
    ResponseEntity<Wallet> getWallet();
}
