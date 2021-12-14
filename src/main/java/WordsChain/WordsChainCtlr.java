package WordsChain;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = WordsChainCtlr.MAPPING_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class WordsChainCtlr {

  public static final String MAPPING_PATH = "/";
  public static final String CHECK_PATH = "";
  public static final HttpMethod CHECK_METHOD = HttpMethod.POST;

  @RequestMapping(method = RequestMethod.POST, path = CHECK_PATH)
  public WordsChainResult check(@RequestBody WordsChainRequest request) {
    int c = 0;
    for(String w : request.words) {
      if(w == null || w.isEmpty() || !w.matches("[a-z]+")
      || (c > 0 && !request.words.get(c - 1).endsWith(w.substring(0, 1)))) {
        break;
      }
      ++c;
    }
    return new WordsChainResult(request.words.subList(0, c));
  }

}
