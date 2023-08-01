package com.issuetrackermax;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;

@MapperScan(basePackages = "com.issuetrackermax", annotationClass = Repository.class)
@SpringBootApplication
public class IssueTrackerMaxApplication {

	public static void main(String[] args) {
		SpringApplication.run(IssueTrackerMaxApplication.class, args);
	}

}
