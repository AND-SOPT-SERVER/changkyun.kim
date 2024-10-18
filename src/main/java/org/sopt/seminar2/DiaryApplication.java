package org.sopt.seminar2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DiaryApplication {
    public static void main(String[] args) {
        SpringApplication.run(DiaryApplication.class, args);
    }
}
