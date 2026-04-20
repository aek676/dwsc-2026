package es.ual.eureka.client.omdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OmdbServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OmdbServiceApplication.class, args);
	}

}
