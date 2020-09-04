package me.toyproject.whatmoviedataimport.application

import com.fasterxml.jackson.databind.JsonNode
import com.jayway.jsonpath.internal.filter.ValueNode
import me.toyproject.whatmoviedataimport.domain.Movie
import me.toyproject.whatmoviedataimport.exception.ExceedUsageException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.client.RestTemplate
import spock.lang.Specification


@SpringBootTest
class MovieDetailServiceTest extends Specification {

    @Autowired
    MovieDetailService movieDetailService;

    def "영화 상세조회 가져오기"() {
        given:
        String code = "20201122"

        when:
        Movie movie = movieDetailService.readDetailByMovieCode(code)

        then:
        movie.getName() == "테넷"
        movie.getStatus() == "개봉"
    }

    def "영화 코드 null 입력시 예외 처리"() {

        when:
        Movie movie = movieDetailService.readDetailByMovieCode(null)

        then:
        thrown IllegalArgumentException
    }

    def "KOFIC API 사용만료시 EMPTY"() {

        given:
        String failJson = "{\n" +
                "\tfaultInfo: {\n" +
                "\t\tmessage: \"키의 하루 이용량을 초과하였습니다.\",\n" +
                "\t\terrorCode: \"320011\"\n" +
                "\t}\n" +
                "}";
        JsonNode node = ValueNode.JsonNode.createJsonNode(failJson);
        def restTemplate = Mock(RestTemplate);

        when:
        Movie movie = movieDetailService.readDetailByMovieCode("20201122")

        then:
        thrown ExceedUsageException
    }
}