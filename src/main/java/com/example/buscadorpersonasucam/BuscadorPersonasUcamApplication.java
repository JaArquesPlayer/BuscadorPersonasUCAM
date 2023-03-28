package com.example.buscadorpersonasucam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class BuscadorPersonasUcamApplication {

    public static void main(String[] args) {
        SpringApplication.run(BuscadorPersonasUcamApplication.class, args);
    }

}
