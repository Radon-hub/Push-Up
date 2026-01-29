package org.radon.pushup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PushUpApplication {

    public static void main(String[] args) {
        SpringApplication.run(PushUpApplication.class, args);
    }

}
