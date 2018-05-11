package com.fintech.modules;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;


public class RestTest {
    private  static Logger logger = LoggerFactory.getLogger(RestTest.class);
    
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> converterList=restTemplate.getMessageConverters();
        HttpMessageConverter<?> converter = new StringHttpMessageConverter();
        converterList.add(0, converter);
        restTemplate.setMessageConverters(converterList);

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        
        String str = "{\"bizKey\":\"CHEN\"}";
        HttpEntity<String> formEntity = new HttpEntity<String>(str, headers);
        
        String result = restTemplate.postForObject("http://127.0.0.1:10029/cspuuid/api/uuid/generate",formEntity,String.class).toString();
        logger.info("getUUID,{}",result);
    }
}
