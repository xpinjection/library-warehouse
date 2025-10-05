package com.xpinjection.junit;

import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayNameGenerator;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

@NoArgsConstructor
public class CamelCaseDisplayNameGenerator extends DisplayNameGenerator.Standard {
    private static final String NESTED_ICON = "↪";

    @Override
    public String generateDisplayNameForClass(Class<?> testClass) {
        var className = super.generateDisplayNameForClass(testClass);
        return splitByCamelCase(className)
                .map(word -> switch (word) {
                    case "Api" -> "API";
                    case "Rest" -> "REST";
                    case "Test" -> "Tests";
                    default -> word;
                }).collect(joining(" "));
    }

    @Override
    public String generateDisplayNameForNestedClass(List<Class<?>> enclosingInstanceTypes, Class<?> nestedClass) {
        var className = super.generateDisplayNameForNestedClass(enclosingInstanceTypes, nestedClass);
        return NESTED_ICON + " " + splitByCamelCase(className)
                .collect(joining(" "));
    }

    @Override
    public String generateDisplayNameForMethod(List<Class<?>> enclosingInstanceTypes, Class<?> testClass, Method testMethod) {
        return splitByCamelCase(testMethod.getName())
                .map(String::toLowerCase)
                .collect(joining(" "));
    }

    private Stream<String> splitByCamelCase(String name) {
        return Arrays.stream(StringUtils.splitByCharacterTypeCamelCase(name));
    }
}
