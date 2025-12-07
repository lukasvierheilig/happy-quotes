package dev.lukas.happyquotes.quotes.api;

import dev.lukas.happyquotes.quotes.exception.QuoteNotFoundException;
import dev.lukas.happyquotes.quotes.model.Quote;
import dev.lukas.happyquotes.quotes.service.QuoteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/quotes")
public class QuotesController {

    private final QuoteService quoteService;

    public QuotesController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }


    @GetMapping
    public Iterable<QuoteResponse> getAllQuotes() {
        List<Quote> quotes = quoteService.getAllQuotes();
        return quotes.stream().map(this::mapToResponse).toList();
    }


    @GetMapping("/{id}")
    public QuoteResponse getQuoteById(@PathVariable UUID id) {
        return quoteService.getQuoteById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new QuoteNotFoundException("Quote with ID " + id + " was not found."));
    }

    @PostMapping
    public ResponseEntity<Void> createNewQuote(@Valid @RequestBody CreateNewQuoteRequest request) {
        Quote newQuote = quoteService.createNewQuote(Quote.newQuote(request.text()));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newQuote.id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/random")
    public QuoteResponse getRandomQuote() {
        return quoteService.randomQuote()
                .map(this::mapToResponse)
                .orElseThrow(() -> new QuoteNotFoundException("No quote found in database."));
    }

    @PutMapping("/{id}")
    public QuoteResponse updateQuote(@PathVariable UUID id, @Valid @RequestBody UpdateQuoteRequest request) {
        return mapToResponse(quoteService.updateQuote(id, request.text()));
    }

    private QuoteResponse mapToResponse(Quote quote) {
        return new QuoteResponse(quote.id(), quote.text());
    }

}
