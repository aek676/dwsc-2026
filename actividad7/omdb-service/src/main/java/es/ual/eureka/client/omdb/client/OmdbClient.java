package es.ual.eureka.client.omdb.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "omdbClient", url = "http://www.omdbapi.com")
public interface OmdbClient {

    @GetMapping("/")
    String getMovie(
            @RequestParam("apikey") String apikey,
            @RequestParam("t") String title,
            @RequestParam("plot") String plot
    );

}