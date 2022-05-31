package com.xpinjection.librarywarehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LibraryWarehouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryWarehouseApplication.class, args);
    }
}
