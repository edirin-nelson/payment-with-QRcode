package com.isdservices.paymentwithqrcode.service;

import com.google.zxing.WriterException;
import com.isdservices.paymentwithqrcode.dto.request.MerchantDetailsDto;

import java.io.IOException;

public interface QRCodeService {
    byte[] generateQRCode(MerchantDetailsDto merchantDetails) throws IOException, WriterException;
}