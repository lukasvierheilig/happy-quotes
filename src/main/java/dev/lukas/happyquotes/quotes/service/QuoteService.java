package dev.lukas.happyquotes.quotes.service;

import dev.lukas.happyquotes.quotes.exception.QuoteNotFoundException;
import dev.lukas.happyquotes.quotes.model.Quote;
import dev.lukas.happyquotes.quotes.repository.QuotesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class QuoteService {

    public static final Logger LOGGER = LoggerFactory.getLogger(QuoteService.class);

    private final QuotesRepository quotesRepository;
    private final QrCodeService qrCodeService;
    private final QrCodeProperties qrCodeProperties;
    private final String QUOTES_PATH = "/api/quotes/";

    public QuoteService(QuotesRepository quotesRepository, QrCodeService qrCodeService, QrCodeProperties qrCodeProperties) {
        this.quotesRepository = quotesRepository;
        this.qrCodeService = qrCodeService;
        this.qrCodeProperties = qrCodeProperties;
    }

    public List<Quote> getAllQuotes() {
        return quotesRepository.findAll();
    }

    public Optional<Quote> getQuoteById(UUID id) {
        LOGGER.trace("Fetching quote with ID: {}", id);
        return quotesRepository.findById(id);
    }

    private Quote createNewQuote(Quote quote) {
        return quotesRepository.save(quote);
    }

    public Quote createNewQuoteWithQrCode(String text) {
        LOGGER.info("Creating new quote with text: {}", text);

        Quote newQuote = createNewQuote(Quote.newQuoteWithoutQrCode(text));

        String qrCodeData = String.join("",
                qrCodeProperties.host(),
                QUOTES_PATH,
                newQuote.id().toString());

        byte[] qrCode = qrCodeService.generateQrCode(qrCodeData, qrCodeProperties.width(), qrCodeProperties.height());

        if (qrCodeProperties.writeToFile()) {
            qrCodeService.writeQrCodeToFile(qrCode, newQuote.id());
        }
        return quotesRepository.save(new Quote(newQuote.id(), newQuote.text(), qrCode));

    }

    public Quote updateQuote(UUID id, String newText) {
        LOGGER.info("Updating quote with ID {} with new text: {}", id, newText);

        Quote quote = quotesRepository.findById(id)
                .orElseThrow(() -> new QuoteNotFoundException("Quote with ID " + id + " does not exist."));

        return quotesRepository.save(new Quote(id, newText, quote.qrCode()));
    }

    public Optional<Quote> randomQuote() {
        return quotesRepository.findRandomQuote();
    }
}
