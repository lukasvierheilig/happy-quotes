package dev.lukas.happyquotes.quotes.api;

import java.util.UUID;

public record QuoteResponse(UUID id, String text) {
}
