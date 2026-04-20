package es.ual.eureka.client.sentence.feign.controller;

import es.ual.eureka.client.sentence.feign.service.SentenceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SentenceController {

  private final SentenceService sentenceService;

  public SentenceController(SentenceService sentenceService) {
    this.sentenceService = sentenceService;
  }

  @GetMapping("/sentences")
  public String getSentence() {
    return sentenceService.getSentence();
  }
}
