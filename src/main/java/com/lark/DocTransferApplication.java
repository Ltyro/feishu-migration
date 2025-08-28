package com.lark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableCaching
@ComponentScan({"com.lark.*"})
@SpringBootApplication
public class DocTransferApplication {
    public DocTransferApplication() {
    }

    public static void main(String[] args) {
        SpringApplication.run(DocTransferApplication.class, new String[0]);
    }
}
