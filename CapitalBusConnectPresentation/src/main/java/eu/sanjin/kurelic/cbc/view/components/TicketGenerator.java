package eu.sanjin.kurelic.cbc.view.components;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.http.MediaType;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class TicketGenerator {

    public static final MediaType IMAGE_TYPE = MediaType.IMAGE_PNG;
    private static final String IMAGE_FORMAT = IMAGE_TYPE.getSubtype();
    public static final int QR_IMAGE_WIDTH = 120;
    public static final int QR_IMAGE_HEIGHT = 120;

    public static byte[] getQrImage(String text) {
        byte[] image = new byte[0];
        try {
            // If application doesn't have required directory authority
            ImageIO.setUseCache(false);
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, QR_IMAGE_WIDTH, QR_IMAGE_HEIGHT);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, IMAGE_FORMAT, out);
            image = out.toByteArray();
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        return image;
    }

}
