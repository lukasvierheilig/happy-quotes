package dev.lukas.happyquotes.quotes.api;

import jakarta.validation.constraints.NotBlank;

public record CreateNewQuoteRequest(@NotBlank String text) {
}
