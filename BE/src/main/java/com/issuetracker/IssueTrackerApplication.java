package com.issuetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class IssueTrackerApplication {
	public static void main(String[] args) {
		SpringApplication.run(IssueTrackerApplication.class, args);
	}

}
