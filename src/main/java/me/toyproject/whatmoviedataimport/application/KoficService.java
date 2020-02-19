package me.toyproject.whatmoviedataimport.application;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class KoficService {

    private final static String MOVIE_COUNT = "100";
    private final static String MOVIE_LIST_URL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json";
    private final RestTemplate restTemplate;

    @Value("${kofickey}")
    private String myKey;

    public Set<String> fetchAllMovieCodes() throws Exception {
        try {
            Set<String> movieCodes = new HashSet<>();
            return IntStream.range(1, 800)
                    .mapToObj(this::fetchMovieBypage)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            throw new Exception();
        }
    }

    private Set<String> fetchMovieBypage(int page) {
        JsonNode response = restTemplate.getForObject(getAllMovieByPage(page), JsonNode.class);
        JsonNode jsonNode = response.get("movieListResult").get("movieList");

        return StreamSupport.stream(jsonNode.spliterator(), false)
                .map(node -> node.get("movieCd").asText())
                .collect(Collectors.toSet());
    }

    private String getAllMovieByPage(int page) {
        return UriComponentsBuilder.fromUriString(MOVIE_LIST_URL)
                .queryParam("key", myKey)
                .queryParam("curPage", page)
                .queryParam("itemPerPage", MOVIE_COUNT)
                .toUriString();
    }
}
