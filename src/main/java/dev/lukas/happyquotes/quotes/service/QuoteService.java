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

    public QuoteService(QuotesRepository quotesRepository) {
        this.quotesRepository = quotesRepository;
    }

    public List<Quote> getAllQuotes() {
        return quotesRepository.findAll();
    }

    public Optional<Quote> getQuoteById(UUID id) {
        LOGGER.trace("Fetching quote with ID: {}", id);
        return quotesRepository.findById(id);
    }

    public Quote createNewQuote(Quote quote) {
        LOGGER.info("Creating new quote with text: {}", quote.text());
        return quotesRepository.save(quote);
    }

    public Quote updateQuote(UUID id, String newText) {
        LOGGER.info("Updating quote with ID {} with new text: {}", id, newText);

        quotesRepository.findById(id)
                .orElseThrow(() -> new QuoteNotFoundException("Quote with ID " + id + " does not exist."));

        return quotesRepository.save(new Quote(id, newText));
    }

    public Optional<Quote> randomQuote() {
        return quotesRepository.findRandomQuote();
    }
}
