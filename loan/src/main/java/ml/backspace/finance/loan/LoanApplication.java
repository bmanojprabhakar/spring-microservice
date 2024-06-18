package ml.backspace.finance.loan;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import ml.backspace.finance.loan.dto.LoansContactInfoDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Loans microservice REST API Documentation",
				description = "Loans microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Manoj Prabhakar",
						email = "bmanojprabhakar@gmail.com",
						url = "http://www.bmanojprabhakar.ml"
				)
		)
)
@EnableConfigurationProperties(value = {LoansContactInfoDto.class})
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class LoanApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanApplication.class, args);
	}

}
