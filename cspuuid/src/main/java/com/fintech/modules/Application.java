package com.fintech.modules;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

/**
 * @author xujunqi
 * @date: 2017/11/17 11:09
 * @description: springboot启动主入口
 */
//@EnableEurekaClient
@EnableTransactionManagement
@SpringBootApplication
@EnableCircuitBreaker
public class Application {
    private static Logger logger = LoggerFactory.getLogger(Application.class);
    
    @Value("${resttemplate.connection.connect-timeout}")
    private Integer connectTimeout;

    @Value("${resttemplate.connection.read-timeout}")
    private Integer readTimeout;
    
    public static void main(String[] args) {
        logger.info("===============Spring Boot start=========================");
        SpringApplication.run(Application.class, args);
        
        logger.info("===============Spring Boot end=========================");
    }

    @Bean("com.fintech.modules.restTemplate")
    //@LoadBalanced
    RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> converterList=restTemplate.getMessageConverters();
        HttpMessageConverter<?> converter = new StringHttpMessageConverter();
        converterList.add(0, converter);
        restTemplate.setMessageConverters(converterList);

        SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        if (connectTimeout > 0) {
            clientHttpRequestFactory.setConnectTimeout(connectTimeout);
            logger.info("配置restTemplate-connectTimeout: {}", connectTimeout);
        }
        if (readTimeout > 0) {
            clientHttpRequestFactory.setReadTimeout(readTimeout);
            logger.info("配置restTemplate-readTimeout: {}", readTimeout);
        }

        restTemplate.setRequestFactory(clientHttpRequestFactory);
        
        return restTemplate;
    }
}
