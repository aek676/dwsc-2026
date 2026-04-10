package es.ual.manager_students.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

  @Value("${springdoc.api-info.title:Manager Students API}")
  private String apiTitle;

  @Value("${springdoc.api-info.description:API for managing students (JPA)}")
  private String apiDescription;

  @Value("${springdoc.api-info.version:1.0.0}")
  private String apiVersion;

  @Value("${springdoc.api-info.contact.name:UAL Team}")
  private String contactName;

  @Value("${springdoc.api-info.contact.email:ual@ugr.es}")
  private String contactEmail;

  @Bean
  public Info apiInfo() {
    return new Info()
        .title(apiTitle)
        .description(apiDescription)
        .version(apiVersion)
        .contact(new Contact()
            .name(contactName)
            .email(contactEmail));
  }
}