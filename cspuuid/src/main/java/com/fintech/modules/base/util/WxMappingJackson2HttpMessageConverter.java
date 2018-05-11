package com.fintech.modules.base.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * @author: xujunqi
 * @date: 2017/11/16 19:31
 * @description: 处理对方返回报文格式不是json的问题
 *                  增加media type对html和plain的支持
 */
public class WxMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
    public WxMappingJackson2HttpMessageConverter(){
        List<MediaType> mediaTypes = new ArrayList<MediaType>();
        mediaTypes.add(MediaType.TEXT_PLAIN);
        //加入text/html类型的支持
        mediaTypes.add(MediaType.TEXT_HTML);
        // tag6
        setSupportedMediaTypes(mediaTypes);
    }
}
