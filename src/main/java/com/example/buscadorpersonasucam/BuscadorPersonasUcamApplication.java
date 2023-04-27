package com.example.buscadorpersonasucam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@EnableRetry
public class BuscadorPersonasUcamApplication {
    public static void main(String[] args) {
        SpringApplication.run(BuscadorPersonasUcamApplication.class, args);
    }
}
