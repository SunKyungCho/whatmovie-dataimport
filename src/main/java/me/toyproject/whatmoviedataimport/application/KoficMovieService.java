package me.toyproject.whatmoviedataimport.application;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.toyproject.whatmoviedataimport.domain.Movie;
import me.toyproject.whatmoviedataimport.repository.MovieUpdateRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KoficMovieService {

    private final static String MOVIE_COUNT = "100";
    private final static String MOVIE_LIST_URL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json";
    private final RestTemplate restTemplate;
    private final MovieUpdateRepository movieUpdateRepository;
    private MovieDetailService movieDetailService;

    @Value("${kofickey}")
    private String myKey;

    public List<Movie> readMovies(int page) {

        JsonNode response = restTemplate.getForObject(getUrl(page), JsonNode.class);
        if (response == null || isExceedUsage(response)) {
            return Collections.emptyList();
        }
        return convertToMovieDetails(response);
    }

    private List<Movie> convertToMovieDetails(JsonNode response) {
        List<Movie> movies = new ArrayList<>();
        for (JsonNode movieNode : response.get("movieListResult").get("movieList")) {
            movies.add(movieDetailService.readDetailByMovieCode(movieNode.get("movieCd").asText()));
        }
        return movies;
    }

    private boolean isExceedUsage(JsonNode movieDetailNode) {
        return movieDetailNode.get("faultInfo").get("errorCode").asText().equals("320011");
    }

    private String getUrl(int page) {
        return UriComponentsBuilder.fromUriString(MOVIE_LIST_URL)
                .queryParam("key", myKey)
                .queryParam("curPage", page)
                .queryParam("itemPerPage", MOVIE_COUNT)
                .queryParam("openStartDt", getCurrentYear())
                .toUriString();
    }

    private int getCurrentYear() {
        return LocalDateTime.now().getYear();
    }
}
