package es.ual.eureka.client.tmdb.exception;

public class MovieNotFoundException extends RuntimeException {

    public MovieNotFoundException(String title) {
        super("Movie not found: " + title);
    }

}