package es.ual.eureka.client.movies_manager.service;

import es.ual.eureka.client.movies_manager.client.OmdbClient;
import es.ual.eureka.client.movies_manager.client.TmdClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class MovieServiceImpl implements MovieService {

  private final OmdbClient omdbClient;
  private final TmdClient tmdClient;

  public MovieServiceImpl(OmdbClient omdbClient, TmdClient tmdClient) {
    this.omdbClient = omdbClient;
    this.tmdClient = tmdClient;
  }

  @Override
  public String checkAndInsertMovie(String title) {
    try {
      CompletableFuture<String> omdbFuture = CompletableFuture.supplyAsync(() -> checkOmdb(title));
      CompletableFuture<String> tmdFuture = CompletableFuture.supplyAsync(() -> checkTmd(title));

      String result = CompletableFuture.anyOf(omdbFuture, tmdFuture)
          .thenApply(r -> (String) r)
          .get(3, TimeUnit.SECONDS);

      if (result != null) {
        return "Movie can be inserted: " + title;
      }
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      Thread.currentThread().interrupt();
    }
    return "Movie cannot be inserted";
  }

  private String checkOmdb(String title) {
    try {
      String response = omdbClient.getMovie(title);
      if (response != null && !response.contains("\"Response\":\"False\"")) {
        return response;
      }
    } catch (Exception e) {
      // Ignore, try next service
    }
    return null;
  }

  private String checkTmd(String title) {
    try {
      String response = tmdClient.getMovie(title);
      if (response != null && response.contains("\"results\":") && !response.contains("\"results\":[]")) {
        return response;
      }
    } catch (Exception e) {
      // Ignore, try next service
    }
    return null;
  }

}
