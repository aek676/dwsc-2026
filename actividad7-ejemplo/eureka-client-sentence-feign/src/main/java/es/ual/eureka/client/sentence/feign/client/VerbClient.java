package es.ual.eureka.client.sentence.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("SAMPLE-CLIENT-VERB")
public interface VerbClient {

  @GetMapping("/")
  public String getWord();
}
