package es.ual.eureka.client.sentence.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SentenceController {

  @Autowired
  private DiscoveryClient discoveryClient;

  @GetMapping("/services/{serviceId}")
  public List<ServiceInstance> getServiceInstances(@PathVariable String serviceId) {
    List<ServiceInstance> list = discoveryClient.getInstances(serviceId);
    if (list.isEmpty())
      throw new RuntimeException("No instances found for service: " + serviceId);

    return list;
  }

  @GetMapping("/sentences")
  public String getSentence() {
    return getWord("SAMPLE-CLIENT-SUBJECT") + " "
        + getWord("SAMPLE-CLIENT-VERB") + " "
        + getWord("SAMPLE-CLIENT-COMPL");
  }

  private String getWord(String serviceId) {
    List<ServiceInstance> serviceList = discoveryClient.getInstances(serviceId);
    if (serviceList.isEmpty())
      return null;

    URI uri = serviceList.get(0).getUri();

    return uri != null ? (new RestTemplate()).getForObject(uri, String.class) : null;
  }
}
