package br.com.lsargus.testesicredi;

import br.com.lsargus.testesicredi.security.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class TestesicrediApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestesicrediApplication.class, args);
	}

}
