package util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AbstractResult {

  public static final int OK = 0;
  public static final String OK_DESC = "OK";

  private int status;
  private String desc;

  public AbstractResult(int status, String desc) {
    this.status = status;
    this.desc = desc;
  }

  public AbstractResult() {
    this(OK, OK_DESC);
  }

}
