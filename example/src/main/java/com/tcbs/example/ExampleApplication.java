package com.tcbs.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@ComponentScan({"com.tcbs"})
@EnableAspectJAutoProxy
@Configuration
@EnableAutoConfiguration
@SpringBootApplication
public class ExampleApplication {
    static Logger logger = LoggerFactory.getLogger(ExampleApplication.class);

    public static void main(String[] args) {
        logger.info("start example application");
        SpringApplication.run(ExampleApplication.class, args);
    }
}
