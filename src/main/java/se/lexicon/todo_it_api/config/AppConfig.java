package se.lexicon.todo_it_api.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "TODO Application", version = "0.1", description = "Application handles Tasks to be done."))
public class AppConfig {

}
