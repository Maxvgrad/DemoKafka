package ru.demo.kafka.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application for work with kafka.
 */
@SpringBootApplication
public class DemoApplication {

    public static final String APP_ROOT_PATH = "/api/kafka/v1";

    public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
