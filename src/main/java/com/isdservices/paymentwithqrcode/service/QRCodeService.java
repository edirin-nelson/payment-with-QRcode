package com.isdservices.paymentwithqrcode.service;

import com.google.zxing.WriterException;

import java.io.IOException;

public interface QRCodeService {
    byte[] generateQRCode(String merchantId) throws IOException, WriterException;
}