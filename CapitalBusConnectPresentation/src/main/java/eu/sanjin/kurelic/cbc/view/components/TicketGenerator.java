package eu.sanjin.kurelic.cbc.view.components;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class TicketGenerator {

    public static final int QR_IMAGE_WIDTH = 150;
    public static final int QR_IMAGE_HEIGHT = 150;

    public static byte[] getQrImage(String text) {
        return getQrImage(text, QR_IMAGE_WIDTH, QR_IMAGE_HEIGHT);
    }

    public static byte[] getQrImage(String text, int width, int height) {
        byte[] image = new byte[0];
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "png", out);
            image = out.toByteArray();
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        return image;
    }

}
