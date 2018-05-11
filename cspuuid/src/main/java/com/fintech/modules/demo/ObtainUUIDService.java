package com.fintech.modules.demo;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

/**
 * 定义 调用 UUID微服务的service类 支持熔断机制 hystrix
 */
@Service("com.fintech.modules.demo.ObtainUUIDService")
public class ObtainUUIDService {
    private  static Logger logger = LoggerFactory.getLogger(ObtainUUIDService.class);

    @Autowired
    @Qualifier("com.fintech.modules.restTemplate")
    private RestTemplate restTemplate;

    /**
     * 使用hystrix 实现熔断机制，触发后执行 getUUIDBack方法
     * 判断是否进行熔断的依据是： 
                    根据bucket中记录的次数，计算错误率。 
                    当错误率超过预设的值（默认是50%）且10秒内超过20个请求，则开启熔断。
                    服务调用的各种结果（成功，异常，超时，拒绝），都会上报给熔断器，计入bucket参与计算。
                    暂停一段时间（默认是5s）之后，允许部分请求通过，若请求都是健康的（RT<250ms）则对请求健康恢复（取消熔断），如果不是健康的，则继续熔断
     * @return
     */
    @HystrixCommand(fallbackMethod = "getUUIDBack"/*,commandProperties = {
            @HystrixProperty(name="execution.isolation.strategy", value="SEMAPHORE")
          }*/
    )
    public String getUUID(String url,String bizKey) throws Exception{
        
        String uuid = null;
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("bizKey", bizKey);
        JSONObject paramJSON = new JSONObject(param);
        HttpEntity<String> formEntity = new HttpEntity<String>(paramJSON.toString(), headers);
        
        String result = restTemplate.postForObject(url,formEntity,String.class).toString();
        logger.debug("getUUID,{}",result);
        if(StringUtils.isEmpty(result)){
            logger.error("getUUID,{}",result);
            throw new Exception("UUID-ERROR");
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        String retCode = jsonObject.getString("retCode");
        if("0000".equals(retCode)){
            uuid = jsonObject.getString("uuid");
        }else{
            logger.error("getUUID,{}",result);
            throw new Exception("UUID-ERROR");
        }
        
        return uuid;
    }

    public String getUUIDBack(String url,String bizKey) throws Exception{
        logger.error("=======getUUIDBack hystrix error");
        throw new Exception("UUID-ERROR");
    }
}
