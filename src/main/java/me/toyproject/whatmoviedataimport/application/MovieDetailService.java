package me.toyproject.whatmoviedataimport.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import me.toyproject.whatmoviedataimport.domain.Movie;
import me.toyproject.whatmoviedataimport.exception.ExceedUsageException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
@RequiredArgsConstructor
public class MovieDetailService {

    private final static String MOVIE_DETAIL_URL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json";
    private final static String OPTION_URL = "http://www.kobis.or.kr/kobis/business/mast/mvie/searchMovieDtl.do?code=";
    private final RestTemplate restTemplate;
    @Value("${kofickey}")
    private String myKey;

    public Movie readDetailByMovieCode(String movieCode) {
        Assert.isTrue(!Strings.isNullOrEmpty(movieCode), "Movie code must not be null or empty.");

        JsonNode movieDetailNode = getMovieJsonNode(movieCode);
        JsonNode movieInfo = movieDetailNode.get("movieInfoResult").get("movieInfo");

        return Movie.builder()
                .movieCode(movieCode)
                .name(movieInfo.get("movieNm").asText())
                .nameEn(movieInfo.get("movieNmEn").asText())
                .actors(convertListToString(movieInfo.get("actors"), "peopleNm"))
                .director(convertListToString(movieInfo.get("directors"), "peopleNm"))
                .genre(convertListToString(movieInfo.get("genres"), "genreNm"))
                .nation(convertListToString(movieInfo.get("nations"), "nationNm"))
                .audit(convertListToString(movieInfo.get("audits"), "watchGradeNm"))
                .openDate(movieInfo.get("openDt").asText())
                .showTime(movieInfo.get("showTm").asText())
                .productionYear(movieInfo.get("prdtYear").asText())
                .status(movieInfo.get("prdtStatNm").asText())
                .description(getDescription(movieCode))
                .imageUrl(getImageUrl(movieCode))
                .build();
    }

    private JsonNode getMovieJsonNode(String movieCode) {
        JsonNode jsonNode = restTemplate.getForObject(getMovieDetailUrl(movieCode), JsonNode.class);
        if(isExceedUsage(jsonNode)) {
            throw new ExceedUsageException("ex");
        }
        return jsonNode;
    }

    private boolean isExceedUsage(JsonNode node) {
        return node.has("faultInfo") && node.get("faultInfo").get("errorCode").asText().equals("320011");
    }

    private String getMovieDetailUrl(String movieCode) {
        return UriComponentsBuilder.fromUriString(MOVIE_DETAIL_URL)
                .queryParam("key", myKey)
                .queryParam("movieCd", movieCode)
                .toUriString();
    }

    private String convertListToString(JsonNode node, String key) {
        return StreamSupport.stream(node.spliterator(), false)
                .limit(20)
                .map(n -> n.get(key).asText())
                .collect(Collectors.joining(","));
    }

    private String getImageUrl(String movieCode) {
        try {
            Document doc = Jsoup.connect(OPTION_URL + movieCode).get();

            String imageUri = doc.select(".info1 a").first().attr("href");
            return imageUri.equals("#") ? null : "http://www.kobis.or.kr" + imageUri;
        } catch (Exception e) {
            return null;
        }
    }

    private String getDescription(String movieCode) {
        try {
            Document doc = Jsoup.connect(OPTION_URL + movieCode).get();
            return doc.select(".desc_info").first().ownText();
        } catch (Exception e) {
            return null;
        }
    }
}
