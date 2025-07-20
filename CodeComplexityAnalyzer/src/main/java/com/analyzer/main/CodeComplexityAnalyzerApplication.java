package com.analyzer.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.analyzer")
public class CodeComplexityAnalyzerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeComplexityAnalyzerApplication.class, args);
	}
}