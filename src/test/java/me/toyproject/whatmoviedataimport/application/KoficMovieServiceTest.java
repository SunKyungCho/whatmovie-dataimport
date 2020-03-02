package me.toyproject.whatmoviedataimport.application;

import me.toyproject.whatmoviedataimport.domain.MovieUpdate;
import me.toyproject.whatmoviedataimport.repository.MovieUpdateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class KoficMovieServiceTest {

    @Autowired
    KoficMovieService koficMovieService;

    @Autowired
    MovieUpdateRepository movieUpdateRepository;

    @Test
    void fetchMovieCodes() throws Exception {
        Set<String> codes = koficMovieService.fetchAllMovieCodes();
        codes.forEach(code -> movieUpdateRepository.save(new MovieUpdate(code)));
    }

    @Test
    void saveMovieUpdate() {
        MovieUpdate movieUpdate = new MovieUpdate("222");
        movieUpdate.updated();
        movieUpdateRepository.save(movieUpdate);
    }


    @Test
    void test() {
        DataSource dataSource = DataSourceBuilder.create()
                .driverClassName("com.mysql.jdbc.Driver")
                .url("jdbc:mysql://192.168.0.164:3306/demo_db?characterEncoding=UTF-8&serverTimezone=UTC")
                .username("search_space")
                .password("tjcltmvpdltm!!")
                .build();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("SELECT count(*) from movie");
        assertThat(maps.size()).isGreaterThan(0);
    }
}