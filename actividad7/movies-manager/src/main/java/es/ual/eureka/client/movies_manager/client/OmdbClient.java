package es.ual.eureka.client.movies_manager.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "OMDB-SERVICE")
public interface OmdbClient {

    @GetMapping("/movie")
    String getMovie(@RequestParam("title") String title);

}