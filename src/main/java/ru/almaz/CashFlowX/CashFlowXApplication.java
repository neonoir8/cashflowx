package ru.almaz.CashFlowX;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CashFlowXApplication {

	public static void main(String[] args) {
		SpringApplication.run(CashFlowXApplication.class, args);
	}

}
