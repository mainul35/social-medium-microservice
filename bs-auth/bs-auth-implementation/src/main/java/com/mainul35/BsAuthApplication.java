package com.mainul35;

import com.mainul35.auth.initialize.InitializeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.mainul35.auth.repositories"})
public class BsAuthApplication implements CommandLineRunner {

//    @Autowired
//    private InitializeData initializeData;

    public static void main(String[] args) {
        SpringApplication.run(BsAuthApplication.class, args);
    }

    @Override
    public void run(String... args) {
//        initializeData.initialize();
    }
}
