package dev.lukas.happyquotes.quotes.exception;

public abstract class QuoteServiceException extends RuntimeException {

    protected QuoteServiceException(String message) {
        super(message);
    }
}
