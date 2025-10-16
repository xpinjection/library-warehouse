package com.xpinjection.librarywarehouse;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ClasspathFileSource;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.standalone.JsonFileMappingsSource;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.core.env.MapPropertySource;

import java.util.Map;

public class WireMockApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext context) {
        var wireMockServer = new WireMockServer(new WireMockConfiguration()
                .dynamicPort()
                .mappingSource(new JsonFileMappingsSource(new ClasspathFileSource("wiremock/library"))));
        wireMockServer.start();

        context.getBeanFactory().registerSingleton("wireMockServer", wireMockServer);

        context.addApplicationListener(applicationEvent -> {
            if (applicationEvent instanceof ContextClosedEvent) {
                wireMockServer.stop();
            }
        });

        var env = context.getEnvironment();
        var dependencies = new MapPropertySource("dependencies", Map.of(
                "feign.library.url", "http://localhost:" + wireMockServer.port()
        ));
        env.getPropertySources().addFirst(dependencies);
    }
}
