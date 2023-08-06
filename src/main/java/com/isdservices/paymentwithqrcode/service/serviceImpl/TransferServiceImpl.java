package com.isdservices.paymentwithqrcode.service.serviceImpl;

import com.isdservices.paymentwithqrcode.dto.request.BankTransferRequest;
import com.isdservices.paymentwithqrcode.dto.request.QRcodeRequest;
import com.isdservices.paymentwithqrcode.dto.response.BankTransferResponse;
import com.isdservices.paymentwithqrcode.dto.response.QRcodeResponse;
import com.isdservices.paymentwithqrcode.model.User;
import com.isdservices.paymentwithqrcode.model.Wallet;
import com.isdservices.paymentwithqrcode.repository.UserRepository;
import com.isdservices.paymentwithqrcode.repository.WalletRepository;
import com.isdservices.paymentwithqrcode.service.TransferService;
import com.isdservices.paymentwithqrcode.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransferServiceImpl implements TransferService {
    private final WalletRepository walletRepo;
    private final SecurityUtils securityUtils;
    private final UserRepository userRepo;
    private final BCryptPasswordEncoder passwordEncoder;


    public ResponseEntity<QRcodeResponse> verifyQRcodeDetails(QRcodeRequest data){
        // Verify the QRCode data
        Wallet wallet = walletRepo.findByAccountNumber(data.getAccountNumber());
        QRcodeResponse response = new QRcodeResponse();

        if(wallet == null){
            throw new UsernameNotFoundException("Account number not found");
        }

        response.setAccountNumber(wallet.getAccountNumber());
        response.setAccountName(wallet.getUser().getFirstName() + " " +wallet.getUser().getLastName());
        if(wallet.getBankName().equals(data.getBankName())){
            response.setBankName(data.getBankName());
        }

        return ResponseEntity.ok(response);
    }

    public  ResponseEntity<BankTransferResponse> bankTransfer(BankTransferRequest request){

        String email = securityUtils.getCurrentUserDetails().getUsername();
        User user = userRepo.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("user not found"));


        //  verify the merchant account details
        Wallet merchantWallet = walletRepo.findByAccountNumber(request.getAccountNumber());
        if(merchantWallet == null){
            throw new UsernameNotFoundException("Account number not found");
        }
        if(!merchantWallet.getBankName().equals(request.getBankName())){
            throw new UsernameNotFoundException("account not recognised with bank");
        }
        if(merchantWallet.getUser().getEmail().equals(email)){
            throw new UsernameNotFoundException("please enter another account number");
        }

        // verify the user account details

        Wallet userWallet = walletRepo.findByUser(user);
        if(userWallet == null){
            throw new UsernameNotFoundException("Account number not found");
        }

        double walletBalance = Double.parseDouble(userWallet.getAccountBalance());
        double transferAmount = Double.parseDouble(request.getAmount());


        if(walletBalance < transferAmount){
            throw new UsernameNotFoundException("account balance not sufficient");
        }

        if(!passwordEncoder.matches(request.getPin(), user.getPin())){
            throw new UsernameNotFoundException("wrong pin");
        }


        //To initiate transaction
        double value = walletBalance - transferAmount;
        //set  userWallet new balance
        userWallet.setAccountBalance(String.valueOf(value));

        log.info("user now account "+user.getWallet().getAccountBalance());

        // add transfer money to merchant account balance
        double newAmount = Double.parseDouble(merchantWallet.getAccountBalance()) + transferAmount;
        merchantWallet.setAccountBalance(String.valueOf(newAmount));

        // save the transactions
        user.setWallet(userWallet);
        userRepo.save(user);
        walletRepo.save(userWallet);
        walletRepo.save(merchantWallet);

        var response = BankTransferResponse.builder()
                .amount_transferred(request.getAmount())
                .transfer_date(LocalDateTime.now())
                .benefactor(merchantWallet.getUser().getFirstName() +" " +merchantWallet.getUser().getLastName())
                .build();

        return ResponseEntity.ok(response);
    }
}
