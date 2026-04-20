package es.ual.eureka.client.omdb.controller;

import es.ual.eureka.client.omdb.service.OmdbService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class MovieController {

    private final OmdbService omdbService;

    public MovieController(OmdbService omdbService) {
        this.omdbService = omdbService;
    }

    @GetMapping
    public ResponseEntity<String> getMovie(@RequestParam("title") String title) {
        String response = omdbService.getMovie(title);
        return ResponseEntity.ok(response);
    }

}