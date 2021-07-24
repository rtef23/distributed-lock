package personal.toy.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import personal.toy.domain.Test;
import personal.toy.infrastructure.TestRepository;
import personal.toy.presentation.dto.TestDto;

@Service
@RequiredArgsConstructor
public class TestService {

  private final TestRepository testRepository;

  public void test(long id, TestDto testDto) {
    Test test = testRepository.findById(id).orElseThrow(IllegalStateException::new);

    try {
      Thread.sleep(4000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    test.addValue(testDto.getValue());
  }
}
