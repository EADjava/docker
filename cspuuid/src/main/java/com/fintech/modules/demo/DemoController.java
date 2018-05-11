package com.fintech.modules.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Configuration
@RequestMapping("/api/demo")
public class DemoController {
    private  static Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    @Qualifier("com.fintech.modules.demo.ObtainUUIDService")
    private ObtainUUIDService biz;

    @Value("${uuid.url}")
    private String uuidURL;
    //  使用ribbon 实现负载均衡
    @RequestMapping(value = "/uuid", method = RequestMethod.GET)
    public String uuid() {
        String result = "UUID-ERROR";
        try {
            
            result = biz.getUUID(uuidURL,"CHEN");
        } catch (Exception e) {
            logger.error("===========helloSome error",e);
        }
        
        return result;
    }
}
