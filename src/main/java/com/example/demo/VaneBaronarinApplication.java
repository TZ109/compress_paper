package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class VaneBaronarinApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(VaneBaronarinApplication.class, args);
		//System.out.print("버전 : "+ org.springframework.core.SpringVersion.getVersion());
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder)
	{ return builder.sources(VaneBaronarinApplication.class);
	}
}
