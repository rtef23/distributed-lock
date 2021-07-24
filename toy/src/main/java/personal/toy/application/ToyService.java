package personal.toy.application;

import java.time.Duration;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.toy.application.lock.service.LockService;
import personal.toy.presentation.dto.TestDto;

@Service
@RequiredArgsConstructor
public class ToyService {

  private final LockService lockService;
  private final TestService testService;

  @PersistenceContext
  private EntityManager entityManager;

  @Transactional
  public void testDistributedLock(long id, TestDto testDto) {
    lockService.executeWithLock("TESTS:" + id, Duration.ofSeconds(3),
        () ->
            testService.test(id, testDto),
        () ->
            entityManager.flush(),
        () ->
            entityManager.clear()
    );
  }
}
