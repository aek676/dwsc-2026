package es.ual.eureka.client.tmdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TmdServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TmdServiceApplication.class, args);
	}

}
