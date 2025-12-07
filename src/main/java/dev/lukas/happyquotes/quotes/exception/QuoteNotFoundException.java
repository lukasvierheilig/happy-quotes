package dev.lukas.happyquotes.quotes.exception;

import java.util.UUID;

public final class QuoteNotFoundException extends QuoteServiceException {

    public QuoteNotFoundException(String message) {
        super(message);
    }
}
