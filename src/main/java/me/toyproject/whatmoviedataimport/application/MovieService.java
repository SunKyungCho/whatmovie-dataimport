package me.toyproject.whatmoviedataimport.application;


import lombok.RequiredArgsConstructor;
import me.toyproject.whatmoviedataimport.domain.Movie;
import me.toyproject.whatmoviedataimport.domain.MovieUpdate;
import me.toyproject.whatmoviedataimport.repository.MovieRepository;
import me.toyproject.whatmoviedataimport.repository.MovieUpdateRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieUpdateRepository movieUpdateRepository;

    @Transactional
    public void saveMovie(Movie movie) {
        movieRepository.save(movie);
        MovieUpdate update = movieUpdateRepository.findByMovieCode(movie.getMovieCode());
        update.updated();
    }
}
