package com.NeDonil.SequenceProcessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SequenceProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SequenceProcessorApplication.class, args);
	}

}
