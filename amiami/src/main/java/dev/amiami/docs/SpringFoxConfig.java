package dev.amiami.docs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@EnableSwagger2
@Import(SpringDataRestConfiguration.class)
@Configuration
public class SpringFoxConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                .useDefaultResponseMessages(false)
                .globalResponses(
                        HttpMethod.GET, new ArrayList<>(
                                Arrays.asList(
                                        new ResponseBuilder()
                                                .code("500")
                                                .description("500 message -> Internal Server Error")
                                                .build(),
                                        new ResponseBuilder()
                                                .code("403")
                                                .description("403 message -> Forbidden")
                                                .build(),
                                        new ResponseBuilder()
                                                .code("404")
                                                .description("404 message -> Not Found")
                                                .build(),
                                        new ResponseBuilder()
                                                .code("400")
                                                .description("400 message -> Bad Request")
                                                .build(),
                                        new ResponseBuilder()
                                                .code("401")
                                                .description("401 message -> Unauthorized / Expired JWT token")
                                                .build(),
                                        new ResponseBuilder()
                                                .code("200")
                                                .description("200 message -> OK")
                                                .build()
                                )
                        )
                )
                .apiInfo(apiInfo());
    }

    private List<SecurityScheme> securitySchemes() {
        return Collections.singletonList(securityScheme());
    }

    private List<springfox.documentation.spi.service.contexts.SecurityContext> securityContexts() {
        return Collections.singletonList(securityContext());
    }

    private SecurityScheme securityScheme() {
        return new ApiKey("Bearer Token", "Authorization", "header");
    }

    private springfox.documentation.spi.service.contexts.SecurityContext securityContext() {
        return springfox.documentation.spi.service.contexts.SecurityContext.builder()
                .securityReferences(securityReferences())
                .build();
    }

    private List<SecurityReference> securityReferences() {
        AuthorizationScope[] authorizationScopes = {
                new AuthorizationScope("global", "accessEverything")
        };
        return Collections.singletonList(
                SecurityReference.builder()
                        .reference("Bearer Token")
                        .scopes(authorizationScopes)
                        .build()
        );
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "AmiAmi API",
                "This API is still in development, however you can use it to upload images and get them back." +
                        "It stores images in a database and GCS and allows you to get them back by their id.",
                "1.0.0",
                "This API is free to use for anyone",
                new Contact("Kyijo", "", "kyijouwu@gmail.com"),
                "API License",
                "",
                Collections.emptyList()
        );
    }
}
