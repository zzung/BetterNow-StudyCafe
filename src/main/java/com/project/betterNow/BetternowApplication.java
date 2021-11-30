package com.project.betterNow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BetternowApplication {
    public static void main(String[] args) {
        SpringApplication.run(BetternowApplication.class, args);
    }
}
