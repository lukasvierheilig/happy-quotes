package dev.lukas.happyquotes.quotes.exception;

public final class QuoteNotFoundException extends QuoteServiceException {

    public QuoteNotFoundException(String message) {
        super(message);
    }
}
