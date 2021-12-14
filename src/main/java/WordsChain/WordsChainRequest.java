package WordsChain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.ToString;

@ToString(callSuper = true)
public class WordsChainRequest {

  public List<String> words;

  public WordsChainRequest() {
    words = new ArrayList<>();
  }

  public WordsChainRequest(String[] words) {
    this.words = Arrays.asList(words);
  }

}
