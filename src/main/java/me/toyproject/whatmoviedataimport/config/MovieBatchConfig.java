package me.toyproject.whatmoviedataimport.config;

import lombok.RequiredArgsConstructor;
import me.toyproject.whatmoviedataimport.domain.Movie;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class MovieBatchConfig {

//    private final JobBuilderFactory jobBuilderFactory;
//    private final StepBuilderFactory stepBuilderFactory;
//
//
//    @Bean
//    public Job movieJob() {
//        return jobBuilderFactory.get("job")
//                .start(simpleStep())
//                .build();
//    }
//
//    @Bean
//    public Step simpleStep() {
//        return stepBuilderFactory.get("test")
//                .<Movie, Movie>chunk(1000)
//                .reader(read())
//                .writer(null)
//                .build();
//    }
//
//    private ItemReader<Movie> read() {
//        return null;
//    }

}
