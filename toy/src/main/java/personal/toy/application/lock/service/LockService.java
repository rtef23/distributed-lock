package personal.toy.application.lock.service;

import java.time.Duration;

public interface LockService {

  void executeWithLock(String lockKey, Duration second, Runnable consumer, Runnable onSuccess, Runnable onError);
}
