package dev.lukas.happyquotes.quotes.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "app.qr-code")
public record QrCodeProperties(
        @DefaultValue("256") int width,
        @DefaultValue("256") int height,
        @DefaultValue("4") int margin,
        @DefaultValue("http://localhost:8080") String host,
        @DefaultValue("false") boolean writeToFile,
        @DefaultValue("src/main/resources/static/qr-codes/") String file
) {
}
