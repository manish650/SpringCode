package com.techprimers.security.springsecurityauthserver;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.techprimers.security.springsecurityauthserver.model.Role;
import com.techprimers.security.springsecurityauthserver.model.Users;
import com.techprimers.security.springsecurityauthserver.repository.UsersRepository;

@SpringBootApplication
public class SpringSecurityAuthServerApplication implements CommandLineRunner {

	@Autowired
	private UsersRepository repository;
	@Autowired
	@Qualifier("bcryptEncoder")
	private PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityAuthServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Role role = new Role();
		role.setRoleId(1);
		role.setRole("USER");
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		Users users = new Users(1, "manish@gmail.com", "manish", encoder.encode("manish"), "mishra", 1, roles);

		Role role1 = new Role();
		role.setRoleId(2);
		role.setRole("ADMIN");
		Set<Role> rolesNew = new HashSet<>();
		rolesNew.add(role1);
		Users users2 = new Users(2, "manish@gmail.com", "man", encoder.encode("man"), "mishra", 1, rolesNew);

		repository.save(users);
		repository.save(users2);
	}
	
	@Bean("bcryptEncoder")
	public PasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}
}
