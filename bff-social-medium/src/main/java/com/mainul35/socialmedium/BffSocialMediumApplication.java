package com.mainul35.socialmedium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.mainul35.socialmedium.config.CorsGlobalConfiguration;

@Import(CorsGlobalConfiguration.class)
@SpringBootApplication
public class BffSocialMediumApplication {

    public static void main(String[] args) {
        SpringApplication.run(BffSocialMediumApplication.class, args);
    }

}
