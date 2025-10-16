package com.xpinjection.librarywarehouse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@Slf4j
@SpringBootApplication
@EnableFeignClients
public class LibraryWarehouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryWarehouseApplication.class, args);
    }
}
