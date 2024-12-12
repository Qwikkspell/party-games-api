package com.qwikkspell.partygamesapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class PartyGamesApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PartyGamesApiApplication.class, args);
	}

}
