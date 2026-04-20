package es.ual.eureka_client.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class WordController {

  @Value("${words}")
  private String words;

  @GetMapping
  public ResponseEntity<String> getWord() {
    String[] wordsArray = words.split(",");
    int index = (int) Math.round(Math.random() * (wordsArray.length - 1));

    return new ResponseEntity<>(wordsArray[index], HttpStatus.OK);
  }
}
