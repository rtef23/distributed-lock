package personal.toy.application;

import java.time.Duration;

public interface LockService {

  void executeWithLock(String lockKey, Duration second, Runnable consumer);
}
