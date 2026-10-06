package br.com.lsargus.testesicredi;

import org.springframework.boot.SpringApplication;

public class TestTestesicrediApplication {

	public static void main(String[] args) {
		SpringApplication.from(TestesicrediApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
