package me.toyproject.whatmoviedataimport.repository;

import me.toyproject.whatmoviedataimport.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
