package com.shramko.parser;

import com.shramko.parser.service.impl.ProcessorImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ParserApplication implements CommandLineRunner {

	private ApplicationContext context;

	@Autowired
	public ParserApplication(ApplicationContext context) {
		this.context = context;
	}

	public static void main(String[] args) {
		SpringApplication.run(ParserApplication.class, args);
	}

	@Override
	public void run(String... args) {
		context.getBean(ProcessorImpl.class).convert(args);
	}
}

