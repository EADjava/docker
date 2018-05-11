package com.fintech.modules.uuid.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

/**
 * @author xujunqi
 * @date: 2018/1/23 13:36
 * @description:
 */
public class UuidReqDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("系统标识 非必填 默认JY 0-6位")
    @Length(min = 0, max = 6, message = "bizKey长度需要在0和6之间")
    private String bizKey="JY";
    @ApiModelProperty("UUID是否组装bizKey默认组装，Y组装，N不组装")
    private String appendBizKey ="Y";

    public UuidReqDTO() {
    }

    public UuidReqDTO(String bizKey) {
        if(StringUtils.isNotEmpty(bizKey)){
            this.bizKey = bizKey;
        }else{
            this.bizKey = "JY";
        }

    }

    public String getBizKey() {
        return bizKey;
    }

    public void setBizKey(String bizKey) {
        if(StringUtils.isNotEmpty(bizKey)){
            this.bizKey = bizKey;
        }else{
            this.bizKey = "JY";
        }
    }
    
    public String getAppendBizKey() {
        return appendBizKey;
    }
    
    public void setAppendBizKey(String appendBizKey) {
        this.appendBizKey = appendBizKey;
    }

}
