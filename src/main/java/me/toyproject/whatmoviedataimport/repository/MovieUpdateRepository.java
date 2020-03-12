package me.toyproject.whatmoviedataimport.repository;

import me.toyproject.whatmoviedataimport.domain.MovieUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieUpdateRepository extends JpaRepository<MovieUpdate, Long> {

    MovieUpdate findByMovieCode(String movieCode);

}
