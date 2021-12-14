package WordsChain;

import SpringTest.MockRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import util.AbstractResult;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class WordsChainCtlrTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
	private Environment env;

  private void okTemplate(Object request, String[] expected) throws Exception {
    WordsChainResult result = MockRequest.perform(mockMvc, WordsChainCtlr.CHECK_METHOD, WordsChainCtlr.MAPPING_PATH + WordsChainCtlr.CHECK_PATH, request, WordsChainResult.class, objectMapper);
    Assertions.assertTrue(result.getStatus() == AbstractResult.OK, result.toString());
    Assertions.assertArrayEquals(expected, result.words.toArray());
  }

  private void okTemplate(String[] input, String[] expected) throws Exception {
    okTemplate(new WordsChainRequest(input), expected);
  }

  @Test
  public void test1() throws Exception {
    okTemplate(
      new String[] { "fish", "horse", "egg", "goose", "eagle" }
    , new String[] { "fish", "horse", "egg", "goose", "eagle" }
    );
  }

  @Test
  public void test2() throws Exception {
    okTemplate(
      new String[] { "fish", "horse", "snake", "goose", "eagle" }
    , new String[] { "fish", "horse" }
    );
  }

  @Test
  public void test3() throws Exception {
    okTemplate(
      new String[] { "fish", "horse", "", "goose", "eagle" }
    , new String[] { "fish", "horse" }
    );
  }

  @Test
  public void test4() throws Exception {
    okTemplate(
      new String[] { "", "horse", "", "goose", "eagle" }
    , new String[] {}
    );
  }

  @Test
  public void emptyList() throws Exception {
    okTemplate(
      new String[] {}
    , new String[] {}
    );
  }

  @Test
  public void nullElement() throws Exception {
    okTemplate(
      new String[] { "fish", "horse", null, "goose", "eagle" }
    , new String[] { "fish", "horse" }
    );
  }

  @Test
  public void badElement() throws Exception {
    Object[] input = { "fish", "horse", 1, "goose", "eagle" };
    String[] expected = { "fish", "horse" };
    HashMap<String, Object> request = new HashMap<>();
    request.put("words", input);
    okTemplate(request, expected);
  }

  @Test
  public void badRequest() throws Exception {
    Object[] input = { "fish", "horse", "egg", "goose", "eagle" };
    String[] expected = {};
    HashMap<String, Object> request = new HashMap<>();
    request.put("word", input);
    okTemplate(request, expected);
  }

  @Test
  public void invalidElement() throws Exception {
    Object[] input = { "fish", "horse", new Object[] { "egg", "grog" }, "goose", "eagle" };
    HashMap<String, Object> request = new HashMap<>();
    request.put("words", input);
    WordsChainResult result = MockRequest.perform(mockMvc, WordsChainCtlr.CHECK_METHOD, WordsChainCtlr.MAPPING_PATH + WordsChainCtlr.CHECK_PATH, request, WordsChainResult.class, objectMapper);
    Assertions.assertNotEquals(AbstractResult.OK, result.getStatus(), result.toString());
  }

  @Test
  public void invalidJson() throws Exception {
    String[] input = { "fish", "horse", null, "goose", "eagle" };
    String request = objectMapper.writeValueAsString(new WordsChainRequest(input)).replace("null", "egg");
    WordsChainResult result = MockRequest.performStringParams(mockMvc, WordsChainCtlr.CHECK_METHOD, WordsChainCtlr.MAPPING_PATH + WordsChainCtlr.CHECK_PATH, request, WordsChainResult.class, objectMapper);
    Assertions.assertNotEquals(AbstractResult.OK, result.getStatus(), result.toString());
  }

  @Test
  public void buildBreak() throws Exception {
    Assertions.assertTrue(env.getProperty("buildBreak") == null, "Test fail! For the build breaking test.");
  }

}
