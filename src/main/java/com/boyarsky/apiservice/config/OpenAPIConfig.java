package com.boyarsky.apiservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "HealthTech REST API", license = @License(name = "MIT", url = "https://github.com/Dje-Bos/healthtech-api-service/blob/develop/LICENSE"), version = "1.0"), security = @SecurityRequirement(name = "JWT"))
@SecurityScheme(type = SecuritySchemeType.APIKEY, name = "JWT", in = SecuritySchemeIn.HEADER,paramName = "Authorization")
public class OpenAPIConfig {
}
