package util;

import javax.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SpringExceptionHandler {

  @ExceptionHandler({ Exception.class })
  @ResponseBody
  public AbstractResult handle(Exception ex, HttpServletRequest request) {
    AbstractResult result = null;
    String exName = ex.getClass().getName();
    if(!"org.apache.catalina.connector.ClientAbortException".equals(exName)) {
      result = new AbstractResult(1, ex.getMessage());//show must go on
    }
    return result;
  }

}
