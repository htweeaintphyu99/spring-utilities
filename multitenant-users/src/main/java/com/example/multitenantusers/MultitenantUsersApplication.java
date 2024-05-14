package com.example.multitenantusers;

import com.example.multitenantusers.model.DatabaseConfig;
import com.example.multitenantusers.model.Role;
import com.example.multitenantusers.model.RoleName;
import com.example.multitenantusers.model.User;
import com.example.multitenantusers.service.DatabaseConfigService;
import com.example.multitenantusers.service.RoleService;
import com.example.multitenantusers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;


@SpringBootApplication
public class MultitenantUsersApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(MultitenantUsersApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleService roleService, UserService userService, DatabaseConfigService databaseConfigService) {
		return args -> {
			if (userService.getAll().size() == 0) {

				Role role_admin = roleService.save(new Role(RoleName.ROLE_ADMIN));
				Role role_user = roleService.save(new Role(RoleName.ROLE_USER));

				User admin = new User("admin", passwordEncoder.encode("admin123"));
				admin.setRoles(Collections.singleton(role_admin));
				userService.save(admin);

				User user = new User("user", passwordEncoder.encode("user123"));
				user.setRoles(Collections.singleton(role_user));
				userService.save(user);

				DatabaseConfig databaseConfig1 = new DatabaseConfig("jdbc:postgresql://localhost:5432/tenant3",
						"postgres", "12345678", "org.postgresql.Driver", "admin");

				DatabaseConfig databaseConfig2 = new DatabaseConfig("jdbc:postgresql://localhost:5432/tenant4",
						"postgres", "12345678", "org.postgresql.Driver", "user");

				databaseConfigService.save(databaseConfig1);
				databaseConfigService.save(databaseConfig2);
			}
		};
	}
}
