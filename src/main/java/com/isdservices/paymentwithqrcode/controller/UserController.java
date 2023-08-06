package com.isdservices.paymentwithqrcode.controller;

import com.isdservices.paymentwithqrcode.dto.request.BankTransferRequest;
import com.isdservices.paymentwithqrcode.dto.request.QRcodeRequest;
import com.isdservices.paymentwithqrcode.dto.response.BankTransferResponse;
import com.isdservices.paymentwithqrcode.dto.response.QRcodeResponse;
import com.isdservices.paymentwithqrcode.model.Wallet;
import com.isdservices.paymentwithqrcode.service.TransferService;
import com.isdservices.paymentwithqrcode.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final WalletService walletService;
    private final TransferService transferService;

    @GetMapping("/wallet-details")
    public ResponseEntity<Wallet> getWallet(){
        return walletService.getWallet();
    }
    @PostMapping("/verify-qrcode")
    public ResponseEntity<QRcodeResponse> verifyQRcode(@RequestBody QRcodeRequest request){
        return transferService.verifyQRcodeDetails(request);
    }

    @PostMapping("/transfer")
    public ResponseEntity<BankTransferResponse> bankTransfer(@RequestBody BankTransferRequest request){
        return transferService.bankTransfer(request);
    }
}
