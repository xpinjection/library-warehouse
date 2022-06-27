package com.xpinjection.librarywarehouse;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

public class LocalLibraryWarehouseApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(LibraryWarehouseApplication.class)
                .initializers(new StandaloneApplicationContextInitializer())
                .applicationStartup(new BufferingApplicationStartup(2048))
                //.applicationStartup(new FlightRecorderApplicationStartup())
                .profiles("dev")
                .run(args);
    }
}
