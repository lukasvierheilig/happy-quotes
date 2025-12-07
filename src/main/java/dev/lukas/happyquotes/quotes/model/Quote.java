package dev.lukas.happyquotes.quotes.model;

import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table(name = "quotes", schema = "happy_quotes")
public record Quote(
        @Id
        @Nullable
        UUID id,

        String text,

        byte @Nullable [] qrCode
) {
    public static Quote newQuoteWithoutQrCode(String text) {
        return new Quote(null, text, null);
    }
}
