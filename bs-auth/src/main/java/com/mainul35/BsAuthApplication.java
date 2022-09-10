package com.mainul35;

import com.mainul35.auth.initialize.InitializeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BsAuthApplication implements CommandLineRunner {

    @Autowired
    private InitializeData initializeData;

    public static void main(String[] args) {
        SpringApplication.run(BsAuthApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        initializeData.initialize();
    }
}
