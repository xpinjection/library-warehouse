package com.xpinjection.test;

import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.annotation.MergedAnnotations.SearchStrategy;

class FeignTestContextBootstrapper extends SpringBootTestContextBootstrapper {
    @Override
    protected String[] getProperties(Class<?> testClass) {
        return MergedAnnotations.from(testClass, SearchStrategy.INHERITED_ANNOTATIONS)
                .get(FeignClientTest.class)
                .getValue("properties", String[].class)
                .orElse(null);
    }
}