package com.fintech.modules.base.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration 
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket api() {
        
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.fintech.modules"))
        .paths(PathSelectors.regex("/api/.*"))//过滤的接口
        //.apis(RequestHandlerSelectors.basePackage("com.fintech.modules.aftloan.callthirdbiz"))
        //.paths(PathSelectors.any())
        
        .build()
        ;
        return docket;
    }
    private ApiInfo apiInfo(){
        ApiInfo apiInfo= (new ApiInfoBuilder())
                .title("集成Swagger项目")
                .description("集成Swagger的Demo API")
                .termsOfServiceUrl("http://localhost:8080/")
                .contact(new Contact("api-swagger","","gangchen1@jieyuechina.com"))
                .version("1.0")
                .build();
        return apiInfo;
    }
    
}  