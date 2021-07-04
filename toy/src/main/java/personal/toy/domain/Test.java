package personal.toy.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "TESTS")
@Getter
public class Test {

  @Id
  private long id;
  private long value;

  public void addValue(long updateValue) {
    this.value += updateValue;
  }
}
