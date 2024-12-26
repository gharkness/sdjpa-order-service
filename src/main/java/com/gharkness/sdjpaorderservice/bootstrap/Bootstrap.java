package com.gharkness.sdjpaorderservice.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("I was called!");
    }
}
