package com.boyarsky.apiservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.collect.Lists.newArrayList;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket healthTechApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(newArrayList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.boyarsky.apiservice.controller.v1"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .enableUrlTemplating(true);
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }
}
