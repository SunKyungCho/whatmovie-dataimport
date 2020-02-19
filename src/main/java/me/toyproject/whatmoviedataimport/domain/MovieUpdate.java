package me.toyproject.whatmoviedataimport.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MovieUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String movieCode;
    private Boolean isUpdated = false;
    @UpdateTimestamp
    private LocalDateTime updateAt;

    public MovieUpdate(String movieCode) {
        this.movieCode = movieCode;
    }
}