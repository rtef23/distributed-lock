package personal.toy.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import personal.toy.domain.Test;

public interface TestRepository extends JpaRepository<Test, Long> {

}
