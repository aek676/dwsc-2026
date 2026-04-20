package es.ual.eureka.client.movies_manager.controller;

import es.ual.eureka.client.movies_manager.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<String> insertMovie(@RequestParam("title") String title) {
        String result = movieService.checkAndInsertMovie(title);
        return ResponseEntity.ok(result);
    }

}