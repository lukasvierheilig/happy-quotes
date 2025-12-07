package dev.lukas.happyquotes.quotes.api;

import org.jspecify.annotations.Nullable;

import java.util.UUID;

public record QuoteResponse(UUID id, String text, @Nullable String qrCode) {
}
