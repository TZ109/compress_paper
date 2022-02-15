package com.baroyoyak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class BaroyoyakApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaroyoyakApplication.class, args);
	}

	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder)
	{ return builder.sources(BaroyoyakApplication.class);
	}
}
