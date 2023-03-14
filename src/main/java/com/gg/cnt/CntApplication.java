package com.gg.cnt;

import com.gg.cnt.config.ApplicationConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationConfigProperties.class})
public class CntApplication {

    public static void main(String[] args) {
        SpringApplication.run(CntApplication.class, args);
    }

}
