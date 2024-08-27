package com.waracle.cakemgr;

import com.waracle.cakemgr.validator.CakeManagerValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class CakeManagerApplication {
	public static void main(String[] args) {
		SpringApplication.run(CakeManagerApplication.class, args);
	}

//	@Bean
//	public CakeManagerValidator cakeManagerValidator() {
//		return new CakeManagerValidator();
//	}
}