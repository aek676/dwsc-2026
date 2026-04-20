package es.ual.eureka.client.sentence.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("SAMPLE-CLIENT-COMPL")
public interface ComplClient {

  @GetMapping("/")
  public String getWord();

}
