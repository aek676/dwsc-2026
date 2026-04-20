package es.ual.eureka.client.movies_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class MoviesManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoviesManagerApplication.class, args);
	}

}
