package com.xpinjection.test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ExtendWith(SpringExtension.class)
@OverrideAutoConfiguration(enabled = false)
@BootstrapWith(FeignTestContextBootstrapper.class)
@TypeExcludeFilters(FeignTypeExcludeFilter.class)
@ImportAutoConfiguration
@AutoConfigureWireMock(port = 0)
@AutoConfigureJson
@AutoConfigureFeign
public @interface FeignClientTest {
    /**
     * Properties in form {@literal key=value} that should be added to the Spring
     * {@link Environment} before the test runs.
     * @return the properties to add
     */
    String[] properties() default {};

    /**
     * Configures WireMock instance to listen on specified port.
     * <p>
     * Set this value to 0 for WireMock to listen to a random port.
     * </p>
     * @return port to which WireMock instance should listen to
     */
    @AliasFor(annotation = AutoConfigureWireMock.class, attribute = "port")
    int port() default 0;

    /**
     * The resource locations to use for loading WireMock mappings.
     * <p>
     * When none specified, <em>src/test/resources/mappings</em> is used as default
     * location.
     * </p>
     * <p>
     * To customize the location, this attribute must be set to the directory where
     * mappings are stored.
     * </p>
     * @return locations to read WireMock mappings from
     */
    @AliasFor(annotation = AutoConfigureWireMock.class, attribute = "stubs")
    String[] stubs() default { "" };

    /**
     * The resource locations to use for loading WireMock response bodies.
     * <p>
     * When none specified, <em>src/test/resources/__files</em> is used as default.
     * </p>
     * <p>
     * To customize the location, this attribute must be set to the parent directory of
     * <strong>__files</strong> directory.
     * </p>
     * @return locations to read WireMock response bodies from
     */
    @AliasFor(annotation = AutoConfigureWireMock.class, attribute = "files")
    String[] files() default { "" };

    /**
     * Determines if default filtering should be used with
     * {@link SpringBootApplication @SpringBootApplication}. By default, only Feign clients
     * and related configuration classes are defined.
     * @see #includeFilters()
     * @see #excludeFilters()
     * @return if default filters should be used
     */
    boolean useDefaultFilters() default true;

    /**
     * A set of include filters which can be used to add otherwise filtered beans to the
     * application context.
     * @return include filters to apply
     */
    ComponentScan.Filter[] includeFilters() default {};

    /**
     * A set of exclude filters which can be used to filter beans that would otherwise be
     * added to the application context.
     * @return exclude filters to apply
     */
    ComponentScan.Filter[] excludeFilters() default {};

    /**
     * Auto-configuration exclusions that should be applied for this test.
     * @return auto-configuration exclusions to apply
     */
    @AliasFor(annotation = ImportAutoConfiguration.class, attribute = "exclude")
    Class<?>[] excludeAutoConfiguration() default {};
}
