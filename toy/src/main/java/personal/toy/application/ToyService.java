package personal.toy.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import personal.toy.domain.Test;
import personal.toy.infrastructure.TestRepository;
import personal.toy.presentation.dto.TestDto;

@Service
@RequiredArgsConstructor
public class ToyService {

  private final TestRepository testRepository;

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void test(long id, TestDto testDto) {
    Test test = testRepository.findById(id).orElseThrow(IllegalStateException::new);

    test.addValue(testDto.getValue());
  }
}
