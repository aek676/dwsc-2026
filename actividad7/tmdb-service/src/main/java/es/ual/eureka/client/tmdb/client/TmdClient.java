package es.ual.eureka.client.tmdb.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tmdClient", url = "${tmd.api.base-url}")
public interface TmdClient {

    @GetMapping("/search/movie")
    String searchMovie(
            @RequestParam("query") String query,
            @RequestParam("api_key") String apiKey
    );

}