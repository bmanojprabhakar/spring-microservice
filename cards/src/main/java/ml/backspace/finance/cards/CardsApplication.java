package ml.backspace.finance.cards;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import ml.backspace.finance.cards.dto.CardsContactInfoDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@OpenAPIDefinition(
		info = @Info(
				title = "Cards microservice REST API Documentation",
				description = "Cards microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Manoj Prabhakar",
						email = "bmanojprabhakar@gmail.com",
						url = "http://www.bmanojprabhakar.ml"
				)
		)
)
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {CardsContactInfoDto.class})
public class CardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardsApplication.class, args);
	}

}
