package com.b2c.tandapay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class TandapayApplication {

	public static void main(String[] args) {

		SpringApplication.run(TandapayApplication.class, args);
		System.out.println("TEST");
	}

}
