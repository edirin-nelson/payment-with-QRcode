package com.isdservices.paymentwithqrcode.service.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.isdservices.paymentwithqrcode.dto.MerchantDetailsDto;
import com.isdservices.paymentwithqrcode.entity.MerchantDetails;
import com.isdservices.paymentwithqrcode.service.QRCodeService;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class QRCodeServiceImpl implements QRCodeService {
    /**
     * Generates a QR code image based on the provided merchant ID.
     *
     * @body merchantDetails The ID of the merchant to be encoded in the QR code.
     * @return The byte array representing the PNG image of the generated QR code.
     * @throws IOException      If there is an issue with reading or writing data during QR code generation.
     * @throws WriterException  If there is an issue with encoding the data into the QR code format.
     */
    @Override
    public byte[] generateQRCode(MerchantDetailsDto merchantDetails) throws IOException, WriterException {
        // Create a JSON representation of the merchant details object
        ObjectMapper objectMapper = new ObjectMapper();
        String merchantDetailsJson = objectMapper.writeValueAsString(merchantDetails);

        // Set the width and height of the QR code image.
        int width = 300;
        int height = 300;

        // Generate the BitMatrix representation of the QR code using the JSON representation of merchant details.
        BitMatrix bitMatrix = new MultiFormatWriter().encode(merchantDetailsJson, BarcodeFormat.QR_CODE, width, height);

        // Convert the BitMatrix to a BufferedImage.
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

        // Write the BufferedImage as a PNG to the ByteArrayOutputStream.
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);

        // Return the byte array representation of the generated QR code.
        return baos.toByteArray();
    }

}
