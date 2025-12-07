package dev.lukas.happyquotes.quotes.repository;

import dev.lukas.happyquotes.quotes.model.Quote;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface QuotesRepository extends ListCrudRepository<Quote, UUID> {
    @Query("""
                select * from happy_quotes.quotes
                order by random()
                limit 1
            """)
    Optional<Quote> findRandomQuote();
}
