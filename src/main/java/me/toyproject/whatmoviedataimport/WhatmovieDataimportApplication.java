package me.toyproject.whatmoviedataimport;

import me.toyproject.whatmoviedataimport.application.KoficMovieService;
import me.toyproject.whatmoviedataimport.domain.MovieUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

import java.util.Set;

@SpringBootApplication
public class WhatmovieDataimportApplication {

    @Autowired
    KoficMovieService koficMovieService;

    public static void main(String[] args) {
        SpringApplication.run(WhatmovieDataimportApplication.class, args);
    }

    @EventListener(ApplicationStartedEvent.class)
    public void init() throws Exception {
        koficMovieService.writeTotalMovieCode();
    }
}
