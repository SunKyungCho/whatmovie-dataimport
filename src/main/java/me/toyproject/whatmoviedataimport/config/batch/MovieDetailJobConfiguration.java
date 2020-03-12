package me.toyproject.whatmoviedataimport.config.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.toyproject.whatmoviedataimport.application.MovieService;
import me.toyproject.whatmoviedataimport.domain.Movie;
import me.toyproject.whatmoviedataimport.domain.MovieUpdate;
import me.toyproject.whatmoviedataimport.repository.MovieRepository;
import me.toyproject.whatmoviedataimport.repository.MovieUpdateRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
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
@EnableBatchProcessing
public class MovieDetailJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final MovieService movieService;


    @Bean
    public Job movieDetailJob() {
        return jobBuilderFactory.get("fetchMovieDetailJob")
                .start(fetchMovieDetailStep())
                .build();
    }

    @Bean
    public Step fetchMovieDetailStep() {
        return stepBuilderFactory.get("fetchMovieDetail")
                .<MovieUpdate, Movie>chunk(100)
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
                movieService.saveMovie(movie);
            }
        };
    }

    @Bean
    public JpaPagingItemReader<MovieUpdate> movieUpdateJpaPagingItemReaderReader() {
        return new JpaPagingItemReaderBuilder<MovieUpdate>()
                .name("getMovieUpdateList")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(100)
                .maxItemCount(2000)
                .queryString("SELECT movie from MovieUpdate as movie WHERE isUpdated = false")
                .build();
    }
}
