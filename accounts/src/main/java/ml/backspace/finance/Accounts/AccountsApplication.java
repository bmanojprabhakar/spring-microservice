package ml.backspace.finance.Accounts;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import ml.backspace.finance.Accounts.dto.ContactInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {ContactInfo.class})
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts microservice REST API Documentation",
				description = "Backspace studio Accounts microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Manoj Prabhakar",
						email = "bmanojprabhakar@gmail.com",
						url = "https://www.bmanojprabhakar.ml"
				)
		)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
