package com.tutorial.springaudit;

import com.tutorial.springaudit.entity.request.RegisterRequest;
import com.tutorial.springaudit.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Slf4j
@SpringBootApplication
@EnableJpaAuditing
public class SpringAuditApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAuditApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service
	) {
		return args -> {
			var admin = RegisterRequest.builder()
					.name("admin")
					.password("password")
					.role("ADMIN")
					.build();
			log.info("Admin: " + service.register(admin));

			var manager = RegisterRequest.builder()
					.name("manager")
					.password("password")
					.role("MANAGER")
					.build();
			log.info("Manager: " + service.register(manager));

		};
	}

}
