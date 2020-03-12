package me.toyproject.whatmoviedataimport.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovieUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long MovieUpdateNo;
    private String movieCode;
    private boolean isUpdated;
    @UpdateTimestamp
    private LocalDateTime updateAt;

    public MovieUpdate(String movieCode) {
        this.movieCode = movieCode;
    }

    public String getMovieCode() {
        return this.movieCode;
    }

    public void updated() {
        this.isUpdated = true;
    }
}