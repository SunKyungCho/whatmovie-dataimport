package me.toyproject.whatmoviedataimport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class WhatmovieDataimportApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhatmovieDataimportApplication.class, args);
    }

}
