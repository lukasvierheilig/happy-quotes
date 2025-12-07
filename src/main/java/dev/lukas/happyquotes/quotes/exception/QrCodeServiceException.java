package dev.lukas.happyquotes.quotes.exception;

public class QrCodeServiceException extends RuntimeException {

    public QrCodeServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
