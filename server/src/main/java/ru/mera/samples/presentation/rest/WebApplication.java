package ru.mera.samples.presentation.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import ru.mera.samples.infrastructure.config.MtomServerConfiguration;

@SpringBootApplication
public class WebApplication {

	public static void main(String[] args) {
        ConfigurableApplicationContext context =
                SpringApplication.run(new Object[] {WebApplication.class, MtomServerConfiguration.class}, args);

        System.out.println("Done.");
	}

}
