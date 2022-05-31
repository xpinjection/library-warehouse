package com.xpinjection.librarywarehouse;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = "com.xpinjection.librarywarehouse",
        importOptions = {ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class, ImportOption.DoNotIncludeArchives.class}
)
public class HexagonalDesignRules {
    static final String SERVICE = "service";
    static final String SERVICE_IMPL = "service-impl";

    static final String ADAPTOR_HTTP_CLIENT = "http-client-adapter";

    @ArchTest
    ArchRule packages_should_follow_hexagonal_design = layeredArchitecture()
            .as("Packages structure should match hexagonal design rules")
            .layer(SERVICE).definedBy("..service", "..service.dto..")
            .layer(SERVICE_IMPL).definedBy("..service.impl..")
            .layer(ADAPTOR_HTTP_CLIENT).definedBy("..adaptors.httpclient..")
            .whereLayer(SERVICE).mayOnlyBeAccessedByLayers(SERVICE_IMPL)
            .whereLayer(ADAPTOR_HTTP_CLIENT).mayOnlyBeAccessedByLayers(SERVICE_IMPL)
            .whereLayer(SERVICE_IMPL).mayNotBeAccessedByAnyLayer();

    @ArchTest
    ArchRule feign_clients_should_be_located_in_http_client_adaptor =
            classes().that().areAnnotatedWith(FeignClient.class)
                    .should().resideInAPackage("..adaptors.httpclient")
                    .andShould().beInterfaces()
                    .andShould().haveNameMatching(".*Client")
                    .as("Feign clients should reside in a http client adaptor package and have corresponding name suffix");

    @ArchTest
    ArchRule service_implementations_should_be_located_in_service_impl =
            classes().that().areAnnotatedWith(Service.class)
                    .should().resideInAPackage("..service.impl")
                    .andShould().haveNameMatching(".*ServiceImpl")
                    .as("Service implementations should reside in a service impl package and have corresponding name suffix");

    @ArchTest
    ArchRule services_should_be_located_in_service =
            classes().that().resideInAPackage("..service")
                    .should().beInterfaces()
                    .andShould().haveNameMatching(".*Service")
                    .as("Services should reside in a service package and have corresponding name suffix");
}
