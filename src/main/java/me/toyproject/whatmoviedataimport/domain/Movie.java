package me.toyproject.whatmoviedataimport.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_no", updatable = false)
    private Long movieNo;
    @Column(name = "movie_code", updatable = false)
    private String movieCode;
    private String name;
    private String nameEn;
    private String director;
    private String genre;
    private String nation;
    private String audit;
    private String showTime;
    private String status;
    private String openDate;
    private String productionYear;
    private String actors;
    private String description;
    private String imageUrl;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createAt;
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt;

    @Builder
    public Movie(String movieCode, String name, String nameEn, String director, String genre, String nation, String audit, String showTime, String status, String openDate, String productionYear, String actors, String description, String imageUrl) {
        this.movieCode = movieCode;
        this.name = name;
        this.nameEn = nameEn;
        this.director = director;
        this.genre = genre;
        this.nation = nation;
        this.audit = audit;
        this.showTime = showTime;
        this.status = status;
        this.openDate = openDate;
        this.productionYear = productionYear;
        this.actors = actors;
        this.description = description;
        this.imageUrl = imageUrl;
    }
}
