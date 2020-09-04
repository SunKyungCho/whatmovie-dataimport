package me.toyproject.whatmoviedataimport.application


import com.fasterxml.jackson.databind.ObjectMapper
import me.toyproject.whatmoviedataimport.domain.Movie
import me.toyproject.whatmoviedataimport.exception.ExceedUsageException
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class MovieDetailMockTest extends Specification {

    def "영화 사용량 초과 예외처리"() {
        given:
        RestTemplate restTemplate = Mock {
            getForObject(_, _) >> new ObjectMapper().readTree("{" +
                    "\"faultInfo\": {" +
                    "\"message\": \"키의 하루 이용량을 초과하였습니다.\"," +
                    "\"errorCode\": \"320011\"" +
                    "}" +
                    "}")
        }
        def service = new MovieDetailService(restTemplate)

        when:
        Movie movie = service.readDetailByMovieCode("20201122")

        then:
        thrown ExceedUsageException
    }
}