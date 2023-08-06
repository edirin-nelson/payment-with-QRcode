package com.isdservices.paymentwithqrcode.service;

import com.isdservices.paymentwithqrcode.dto.request.BankTransferRequest;
import com.isdservices.paymentwithqrcode.dto.request.QRcodeRequest;
import com.isdservices.paymentwithqrcode.dto.response.BankTransferResponse;
import com.isdservices.paymentwithqrcode.dto.response.QRcodeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface TransferService {
    ResponseEntity<BankTransferResponse> bankTransfer(BankTransferRequest request);
    ResponseEntity<QRcodeResponse> verifyQRcodeDetails(QRcodeRequest data);
}
