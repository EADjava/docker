package com.fintech.modules.uuid.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fintech.modules.uuid.enums.UuidRetCodeEnum;

/**
 * @author xujunqi
 * @date: 2018/1/23 13:36
 * @description:
 */
public class UuidRespDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("系统标识")
    private String bizKey;
    @ApiModelProperty("uuid的数据")
    private String uuid;
    @ApiModelProperty("返回时间")
    private String respTime;

    @ApiModelProperty("返回状态（0000成功，1000校验失败，2000失败）")
    private String retCode;

    @ApiModelProperty("返回消息")
    private String retMsg;

    public UuidRespDTO() {
        this(null, null, null, null);
    }

    public UuidRespDTO(String bizKey) {
        this(bizKey, null);
    }

    public UuidRespDTO(String bizKey, String uuid) {
        this(bizKey, uuid, UuidRetCodeEnum.CSHX_RETCODE_SUCC.getCode(), UuidRetCodeEnum.CSHX_RETCODE_SUCC.getMsg());
    }

    public UuidRespDTO(String bizKey, String uuid, String retCode, String retMsg) {
        this.bizKey = bizKey;
        this.uuid = uuid;
        this.respTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    public String getBizKey() {
        return bizKey;
    }

    public void setBizKey(String bizKey) {
        this.bizKey = bizKey;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRespTime() {
        return respTime;
    }

    public void setRespTime(String respTime) {
        this.respTime = respTime;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public void fail(String retCode, String retMsg) {
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    public void fail(UuidRetCodeEnum uuidRetCodeEnum) {
        this.retCode = uuidRetCodeEnum.getCode();
        this.retMsg = uuidRetCodeEnum.getMsg();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UuidRespDTO{");
        sb.append("bizKey='").append(bizKey).append('\'');
        sb.append(", uuid='").append(uuid).append('\'');
        sb.append(", respTime='").append(respTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
