package dev.lukas.happyquotes;

import dev.lukas.happyquotes.quotes.service.QrCodeProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(QrCodeProperties.class)
public class HappyQuotesApplication {

    public static void main(String[] args) {
        SpringApplication.run(HappyQuotesApplication.class, args);
    }

}
