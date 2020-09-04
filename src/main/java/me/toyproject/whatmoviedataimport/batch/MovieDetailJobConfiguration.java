package me.toyproject.whatmoviedataimport.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.toyproject.whatmoviedataimport.application.MovieService;
import me.toyproject.whatmoviedataimport.domain.Movie;
import me.toyproject.whatmoviedataimport.domain.MovieUpdate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
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

    @Bean
    public Job movieDetailJob(JobBuilderFactory jobBuilderFactory) {
        return jobBuilderFactory.get("fetchMovieDetailJob")
                .start(fetchMovieDetailStep(null))
                .build();
    }

    @Bean
    public Step fetchMovieDetailStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("fetchMovieDetail")
                .<MovieUpdate, Movie>chunk(100)
                .reader(movieUpdateJpaPagingItemReaderReader(null))
                .processor(movieDetailProcessor())
                .writer(writer(null))
                .build();
    }

    @Bean
    public ItemProcessor<MovieUpdate, Movie> movieDetailProcessor() {
        return new MovieDetailProcessor();
    }

    @Bean
    public ItemWriter<Movie> writer(MovieService movieService) {
        return list -> {
            log.info("Writing... " + list.size());
            for (Movie movie : list) {
                movieService.saveMovie(movie);
            }
        };
    }

    @Bean
    public JpaPagingItemReader<MovieUpdate> movieUpdateJpaPagingItemReaderReader(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<MovieUpdate>()
                .name("getMovieUpdateList")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(100)
                .maxItemCount(2500)
                .queryString("SELECT movie from MovieUpdate as movie WHERE isUpdated = false")
                .build();
    }
}
