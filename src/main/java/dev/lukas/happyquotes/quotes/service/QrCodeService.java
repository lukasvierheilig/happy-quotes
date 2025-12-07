package dev.lukas.happyquotes.quotes.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import dev.lukas.happyquotes.quotes.exception.QrCodeServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

@Service
public class QrCodeService {

    public static final Logger LOGGER = LoggerFactory.getLogger(QrCodeService.class);
    private final QrCodeProperties qrCodeProperties;

    private final Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);

    public QrCodeService(QrCodeProperties qrCodeProperties) {
        this.qrCodeProperties = qrCodeProperties;
        hints.put(EncodeHintType.MARGIN, qrCodeProperties.margin());
    }


    public byte[] generateQrCode(String data, int width, int height) {
        LOGGER.info("Generating QR code with data: {}", data);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix = null;
        try {
            bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height, hints);
        } catch (WriterException e) {
            throw new QrCodeServiceException(e.getMessage(), e);
        }

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        try {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        } catch (IOException e) {
            throw new QrCodeServiceException(e.getMessage(), e);
        }

        return pngOutputStream.toByteArray();
    }

    public Path writeQrCodeToFile(byte[] qrCodeBytes, UUID quoteId) {

        Path directory = Paths.get(qrCodeProperties.file());
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                throw new QrCodeServiceException(e.getMessage(), e);
            }
        }

        String filename = "quote-" + quoteId + ".png";
        LOGGER.info("Writing QR code to file: {}", filename);
        Path filePath = directory.resolve(filename);

        try {
            Files.write(filePath, qrCodeBytes);
        } catch (IOException e) {
            throw new QrCodeServiceException(e.getMessage(), e);
        }

        return filePath;
    }
}
