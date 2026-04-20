package es.ual.eureka.client.omdb.exception;

public class MovieNotFoundException extends RuntimeException {

    public MovieNotFoundException(String title) {
        super("Movie not found: " + title);
    }

}