package es.ual.eureka.client.tmdb.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.ual.eureka.client.tmdb.client.TmdClient;
import es.ual.eureka.client.tmdb.exception.MovieNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TmdServiceImpl implements TmdService {

    private final TmdClient tmdClient;
    private final ObjectMapper objectMapper;

    @Value("${tmd.api.key}")
    private String apiKey;

    public TmdServiceImpl(TmdClient tmdClient) {
        this.tmdClient = tmdClient;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public String getMovie(String title) {
        String response = tmdClient.searchMovie(title, apiKey);

        try {
            JsonNode json = objectMapper.readTree(response);
            JsonNode results = json.get("results");
            if (results == null || results.isEmpty() || results.size() == 0) {
                throw new MovieNotFoundException(title);
            }
            return response;
        } catch (MovieNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error parsing TMDB response", e);
        }
    }

}