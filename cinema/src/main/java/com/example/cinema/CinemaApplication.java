package com.example.cinema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CinemaApplication {
    public static void main(String[] args) {
        SpringApplication.run(CinemaApplication.class, args);
        System.out.println("  КИНОТЕАТР БЭКЕНД ЗАПУЩЕН");
        System.out.println("  API доступен по адресу: http://localhost:8080/api/movies");
        
    }
}