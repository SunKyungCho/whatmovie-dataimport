package me.toyproject.whatmoviedataimport.batch;

import lombok.RequiredArgsConstructor;
import me.toyproject.whatmoviedataimport.application.KoficMovieService;
import me.toyproject.whatmoviedataimport.domain.Movie;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.stereotype.Component;

import java.util.concurrent.CopyOnWriteArrayList;

@Component
@RequiredArgsConstructor
public class MovieReader extends AbstractPagingItemReader {

    private KoficMovieService koficMovieService;

    @Override
    protected void doReadPage() {
        if (results == null) {
            results = new CopyOnWriteArrayList<>();
        }
        else {
            results.clear();
        }

        int page = getPage();


    }

    @Override
    protected void doJumpToPage(int itemIndex) {

    }
}
