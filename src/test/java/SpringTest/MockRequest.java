package SpringTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class MockRequest {

  public static <R> R performStringParams(MockMvc mockMvc, HttpMethod method, String url, String params, Class<R> resultClass, ObjectMapper objectMapper) throws Exception {
    //System.out.println(">>> url=" + url + "\nparams=" + params);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
    .request(method, url)
    .contentType(MediaType.APPLICATION_JSON_VALUE)
    .content(params)
    ;
    String response = mockMvc.perform(requestBuilder)
    .andDo(MockMvcResultHandlers.print())
    .andExpect(MockMvcResultMatchers.status().isOk())
    .andReturn().getResponse().getContentAsString()
    ;
    return objectMapper.readValue(response, resultClass);
  }

  public static <R> R perform(MockMvc mockMvc, HttpMethod method, String url, Object params, Class<R> resultClass, ObjectMapper objectMapper) throws Exception {
    return performStringParams(mockMvc, method, url, objectMapper.writeValueAsString(params), resultClass, objectMapper);
  }

}
