package me.toyproject.whatmoviedataimport.application;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.toyproject.whatmoviedataimport.domain.MovieUpdate;
import me.toyproject.whatmoviedataimport.repository.MovieUpdateRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class KoficMovieService {

    private final static String MOVIE_COUNT = "100";
    private final static String MOVIE_LIST_URL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json";
    private final RestTemplate restTemplate;
    private final MovieUpdateRepository movieUpdateRepository;

    @Value("${kofickey}")
    private String myKey;

    public void writeTotalMovieCode() throws Exception {
        try {
            for (int page = 1; page < 800; page++) {
                saveTotalMovieCodes(page);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void saveTotalMovieCodes(int page) {
        log.info("Kofic api fetch moive Page: " + page);
        JsonNode response = restTemplate.getForObject(getAllMovieByPage(page), JsonNode.class);
        JsonNode jsonNode = response.get("movieListResult").get("movieList");

        for (JsonNode node : jsonNode) {
            String movieCd = node.get("movieCd").asText();
            movieUpdateRepository.save(new MovieUpdate(movieCd));
        }
    }

    private String getAllMovieByPage(int page) {
        return UriComponentsBuilder.fromUriString(MOVIE_LIST_URL)
                .queryParam("key", myKey)
                .queryParam("curPage", page)
                .queryParam("itemPerPage", MOVIE_COUNT)
                .toUriString();
    }
}
