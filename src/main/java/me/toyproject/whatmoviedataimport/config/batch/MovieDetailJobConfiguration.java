package me.toyproject.whatmoviedataimport.config.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.toyproject.whatmoviedataimport.domain.Movie;
import me.toyproject.whatmoviedataimport.domain.MovieUpdate;
import me.toyproject.whatmoviedataimport.repository.MovieRepository;
import me.toyproject.whatmoviedataimport.repository.MovieUpdateRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class MovieDetailJobConfiguration {


    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final MovieRepository movieRepository;
    private final MovieUpdateRepository movieUpdateRepository;


    @Bean
    public Job movieDetailJob() {
        return jobBuilderFactory.get("fetchMovieDetailJob")
                .start(fetchMovieDetailStep())
                .build();
    }

    @Bean
    public Step fetchMovieDetailStep() {
        return stepBuilderFactory.get("fetchMovieDetail")
                .<MovieUpdate, Movie>chunk(10)
                .reader(movieUpdateJpaPagingItemReaderReader())
                .processor(movieDetailProcessor())
                .writer(writer())
                .build();
    }

    @Bean
    public ItemProcessor<MovieUpdate, Movie> movieDetailProcessor() {
        return new MovieDetailProcessor();
    }

    @Bean
    @StepScope
    public ItemWriter<Movie> writer() {
        return list -> {
            for (Movie movie : list) {
                log.info("Current Movie={}", movie.toString());
                movieRepository.save(movie);
                MovieUpdate update = movieUpdateRepository.findByMovieCode(movie.getMovieCode());
                update.setIsUpdated(true);
            }
        };
    }

    @Bean
    public JpaPagingItemReader<MovieUpdate> movieUpdateJpaPagingItemReaderReader() {
        return new JpaPagingItemReaderBuilder<MovieUpdate>()
                .name("getMovieUpdateList")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(10)
                .maxItemCount(2000)
                .queryString("SELECT movie from MovieUpdate as movie WHERE isUpdated = false")
                .build();
    }
}
