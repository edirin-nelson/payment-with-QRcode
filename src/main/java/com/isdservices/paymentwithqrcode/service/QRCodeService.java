package com.isdservices.paymentwithqrcode.service;

import com.google.zxing.WriterException;
import com.isdservices.paymentwithqrcode.dto.MerchantDetailsDto;
import com.isdservices.paymentwithqrcode.entity.MerchantDetails;

import java.io.IOException;

public interface QRCodeService {
    byte[] generateQRCode(MerchantDetailsDto merchantDetails) throws IOException, WriterException;
}