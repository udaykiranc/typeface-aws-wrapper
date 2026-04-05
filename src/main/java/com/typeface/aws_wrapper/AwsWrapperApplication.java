package com.typeface.aws_wrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class AwsWrapperApplication {
    public static void main(String[] args) {
        SpringApplication.run(AwsWrapperApplication.class, args);
    }
}
