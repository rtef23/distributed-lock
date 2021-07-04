package personal.toy.presentation.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import personal.toy.domain.Test;

@Getter
@Setter
public class TestDto {

  private long value;

  public Test toTest() {
    Test result = new Test();

    BeanUtils.copyProperties(this, result);

    return result;
  }
}
