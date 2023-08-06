package com.isdservices.paymentwithqrcode.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankTransferResponse {
    private String amount_transferred;
    private String benefactor;
    private LocalDateTime transfer_date;
}
