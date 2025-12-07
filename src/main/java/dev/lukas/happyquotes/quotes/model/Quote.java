package dev.lukas.happyquotes.quotes.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table(name = "quotes", schema = "happy_quotes")
public record Quote(
        @Id
        UUID id,

        String text
) {
    public static Quote newQuote(String text) {
        return new Quote(null, text);
    }
}
