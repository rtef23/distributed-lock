package personal.toy.application;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.toy.domain.Test;
import personal.toy.infrastructure.TestRepository;
import personal.toy.presentation.dto.TestDto;

@Service
@RequiredArgsConstructor
public class ToyService {

  private final TestRepository testRepository;

  @PersistenceContext
  private EntityManager entityManager;

  //  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @Transactional
  public void test(long id, TestDto testDto) {
    Test test = testRepository.findById(id).orElseThrow(IllegalStateException::new);

    test.addValue(testDto.getValue());

    entityManager.flush();
  }
}
