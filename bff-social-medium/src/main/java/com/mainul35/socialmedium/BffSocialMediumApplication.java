package com.mainul35.socialmedium;

import com.mainul35.socialmedium.config.CorsGlobalConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(CorsGlobalConfiguration.class)
@SpringBootApplication
public class BffSocialMediumApplication {

    public static void main(String[] args) {
        SpringApplication.run(BffSocialMediumApplication.class, args);
    }

}
