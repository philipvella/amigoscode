package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.JANUARY;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Student mike = new Student(
                    "Mike",
                    LocalDate.of(1990, JANUARY, 1),
                    "mikeTest@mail.com"
            );
            Student frank = new Student(
                    "Frank",
                    LocalDate.of(2000, JANUARY, 1),
                    "frankTest@mail.com"
            );
            studentRepository.saveAll(List.of(frank,mike));
        };
    }
}
