package es.ual.eureka.client.omdb.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.ual.eureka.client.omdb.client.OmdbClient;
import es.ual.eureka.client.omdb.exception.MovieNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OmdbServiceImpl implements OmdbService {

    private final OmdbClient omdbClient;
    private final ObjectMapper objectMapper;

    @Value("${omdb.api.key}")
    private String apiKey;

    public OmdbServiceImpl(OmdbClient omdbClient) {
        this.omdbClient = omdbClient;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public String getMovie(String title) {
        String response = omdbClient.getMovie(apiKey, title, "full");

        try {
            JsonNode json = objectMapper.readTree(response);
            if ("False".equals(json.get("Response").asText())) {
                throw new MovieNotFoundException(title);
            }
            return response;
        } catch (MovieNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error parsing OMDb response", e);
        }
    }

}