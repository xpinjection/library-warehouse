package com.xpinjection.test;

import feign.*;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import org.springframework.boot.test.autoconfigure.filter.StandardAnnotationCustomizableTypeExcludeFilter;
import org.springframework.cloud.openfeign.FeignLoggerFactory;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class FeignTypeExcludeFilter extends StandardAnnotationCustomizableTypeExcludeFilter<FeignClientTest> {
    private static final Set<Class<?>> DEFAULT_INCLUDES;

    static {
        Set<Class<?>> includes = new LinkedHashSet<>();
        includes.add(Decoder.class);
        includes.add(Encoder.class);
        includes.add(Contract.class);
        includes.add(Logger.class);
        includes.add(Logger.Level.class);
        includes.add(FeignLoggerFactory.class);
        includes.add(Feign.Builder.class);
        includes.add(Client.class);
        includes.add(Retryer.class);
        includes.add(ErrorDecoder.class);
        includes.add(Request.Options.class);
        includes.add(RequestInterceptor.class);
        includes.add(QueryMapEncoder.class);
        DEFAULT_INCLUDES = Collections.unmodifiableSet(includes);
    }

    FeignTypeExcludeFilter(Class<?> testClass) {
        super(testClass);
    }

    @Override
    protected Set<Class<?>> getDefaultIncludes() {
        return DEFAULT_INCLUDES;
    }
}
