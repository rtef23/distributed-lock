package personal.toy.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import personal.toy.application.lock.service.LockService;
import personal.toy.application.ToyService;
import personal.toy.presentation.dto.TestDto;

@RestController
@RequiredArgsConstructor
public class TestController {

  private final LockService lockService;
  private final ToyService toyService;

  @PostMapping("/tests/{id}")
  public void test(@PathVariable long id, @RequestBody TestDto testDto) {
    toyService.testDistributedLock(id, testDto);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void handleIllegalArgumentException() {
  }
}
