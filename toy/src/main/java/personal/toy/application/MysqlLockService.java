package personal.toy.application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MysqlLockService implements LockService {

  private static final String GET_LOCK_QUERY = "SELECT GET_LOCK(?, ?)";
  private static final String RELEASE_LOCK_QUERY = "SELECT RELEASE_LOCK(?)";

  private final DataSource dataSource;

  @Override
  public void executeWithLock(String lockKey, Duration duration, Runnable runnable) {
    try (Connection connection = dataSource.getConnection()) {
      try {
        acquireLock(connection, lockKey, duration);

        runnable.run();
      } finally {
        releaseLock(connection, lockKey);
      }
    } catch (SQLException throwables) {
      throw new RuntimeException(throwables);
    }
  }

  private void acquireLock(Connection connection, String lockKey, Duration duration)
      throws SQLException {
    try (
        PreparedStatement preparedStatement = makePreparedStatementForGetLock(connection, lockKey,
            duration);
    ) {
      int retryCount = 0;

      do {
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
          if (resultSet.getInt(1) == 1) {
            break;
          } else {
            Thread.sleep(1000);

            if (retryCount >= 3) {
              throw new IllegalStateException("acquire lock failed. lockKey : " + lockKey);
            }
          }
        } else {
          Thread.sleep(1000);
        }

        retryCount++;
      } while (true);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private PreparedStatement makePreparedStatementForGetLock(Connection connection, String lockKey,
      Duration duration)
      throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement(GET_LOCK_QUERY);

    preparedStatement.setString(1, lockKey);
    preparedStatement.setLong(2, duration.toSeconds());

    return preparedStatement;
  }

  private void releaseLock(Connection connection, String lockKey) throws SQLException {
    try (PreparedStatement preparedStatement = makePreparedStatementForReleaseLock(connection,
        lockKey);
        ResultSet resultSet = preparedStatement.executeQuery();
    ) {
      if (!resultSet.next()) {
        throw new IllegalStateException("release lock failed. lockKey : " + lockKey);
      }

      if (resultSet.getInt(1) != 1) {
        throw new IllegalStateException("release lock failed. lockKey : " + lockKey);
      }
    }
  }

  private PreparedStatement makePreparedStatementForReleaseLock(Connection connection,
      String lockKey)
      throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement(RELEASE_LOCK_QUERY);

    preparedStatement.setString(1, lockKey);

    return preparedStatement;
  }
}
