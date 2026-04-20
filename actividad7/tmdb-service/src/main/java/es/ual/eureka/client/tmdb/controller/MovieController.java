package es.ual.eureka.client.tmdb.controller;

import es.ual.eureka.client.tmdb.service.TmdService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class MovieController {

    private final TmdService tmdService;

    public MovieController(TmdService tmdService) {
        this.tmdService = tmdService;
    }

    @GetMapping
    public ResponseEntity<String> getMovie(@RequestParam("title") String title) {
        String response = tmdService.getMovie(title);
        return ResponseEntity.ok(response);
    }

}