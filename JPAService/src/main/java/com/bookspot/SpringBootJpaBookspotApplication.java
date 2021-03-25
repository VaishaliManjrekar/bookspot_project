package com.bookspot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//@SpringBootApplication
@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class SpringBootJpaBookspotApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJpaBookspotApplication.class, args);
		System.out.println("Bookspot app");
	}

}
