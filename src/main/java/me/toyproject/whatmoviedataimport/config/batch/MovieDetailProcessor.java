package me.toyproject.whatmoviedataimport.config.batch;

import com.fasterxml.jackson.databind.JsonNode;
import me.toyproject.whatmoviedataimport.domain.Movie;
import me.toyproject.whatmoviedataimport.domain.MovieUpdate;
import me.toyproject.whatmoviedataimport.exception.ExceedUsageException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class MovieDetailProcessor implements ItemProcessor<MovieUpdate, Movie> {

    private final static String MOVIE_DETAIL_URL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json";
    private final RestTemplate restTemplate;
    @Value("${kofickey}")
    private String myKey;

    public MovieDetailProcessor() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public Movie process(MovieUpdate movieUpdate) throws Exception {
        String movieCode = movieUpdate.getMovieCode();
        JsonNode movieInfo = fetchMovieDetailJsonNode(movieCode);

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

    private JsonNode fetchMovieDetailJsonNode(String movieCode) throws ExceedUsageException {
        JsonNode movieDetailNode = restTemplate.getForObject(getMovieDetailUrl(movieCode), JsonNode.class);
//        if (movieDetailNode != null && isExceedUsage(movieDetailNode)) {
////            throw new ExceedUsageException("You have exceeded your key daily usage on Kofic api");
//
//        }
        return movieDetailNode.get("movieInfoResult").get("movieInfo");
    }

    private String getImageUrl(String movieCode) {
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.kobis.or.kr/kobis/business/mast/mvie/searchMovieDtl.do?code=" + movieCode).get();
            String imageUri = doc.select(".info1 a").first().attr("href");
            return imageUri.equals("#") ? null : "http://www.kobis.or.kr" + imageUri;
        } catch (Exception e) {
            return null;
        }
    }

    private String getDescription(String movieCode) {
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.kobis.or.kr/kobis/business/mast/mvie/searchMovieDtl.do?code=" + movieCode).get();
            return doc.select(".desc_info").first().ownText();
        } catch (Exception e) {
            return null;
        }
    }

    private boolean isExceedUsage(JsonNode movieDetailNode) {
        return movieDetailNode.get("faultInfo").get("errorCode").asText().equals("320011");
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
}
