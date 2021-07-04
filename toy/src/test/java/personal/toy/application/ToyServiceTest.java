package personal.toy.application;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

class ToyServiceTest {

  @Test
  public void test() {
    RestTemplate restTemplate = new RestTemplateBuilder()
        .build();

    List<String> responseTexts = IntStream.range(0, 100)
        .parallel()
        .mapToObj(number -> {
          long id = 1;
          TestValueDto testValueDto = new TestValueDto(100);

          ResponseEntity<String> responseEntity = null;

          try {
            responseEntity = restTemplate
                .exchange("http://localhost:8001/tests/" + id, HttpMethod.POST,
                    httpEntity(testValueDto),
                    String.class);

            return String
                .format("[%d th try] id : %d addValue : %d response status : %s", number, id,
                    testValueDto.getValue(),
                    responseEntity.getStatusCode());
          } catch (Exception e) {
            return String
                .format("[%d th try] id : %d addValue : %d response status : %s", number, id,
                    testValueDto.getValue(),
                    Objects.isNull(responseEntity) ? "500" : responseEntity.getStatusCode());
          }

        }).collect(Collectors.toList());

    responseTexts.forEach(System.out::println);
  }

  private HttpEntity<TestValueDto> httpEntity(TestValueDto testValueDto) {
    HttpHeaders headers = new HttpHeaders();

    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

    return new HttpEntity<>(testValueDto, headers);
  }

  @AllArgsConstructor
  @Getter
  private static class TestValueDto {

    private long value;
  }
}