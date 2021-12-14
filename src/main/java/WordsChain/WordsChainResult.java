package WordsChain;

import java.util.ArrayList;
import java.util.List;
import lombok.ToString;
import util.AbstractResult;

@ToString(callSuper = true)
public class WordsChainResult extends AbstractResult {

  public List<String> words;

  public WordsChainResult() {
    words = new ArrayList<>();
  }

  public WordsChainResult(List<String> words) {
    this.words = words;
  }

}
